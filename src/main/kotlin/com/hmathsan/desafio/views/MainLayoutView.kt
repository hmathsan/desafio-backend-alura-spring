package com.hmathsan.desafio.views

import com.hmathsan.desafio.services.SecurityService
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.router.RouterLink
import org.springframework.beans.factory.annotation.Autowired

class MainLayoutView(
    @Autowired
    private val securityService: SecurityService
) : AppLayout() {


    init {
        val logo = H1("SAF")
        logo.style
            .set("font-size", "var(--lumo-font-size-l)")
            .set("left", "var(--lumo-space-l)")
            .set("margin", "0")
            .set("position", "absolute")
        logo.addClassName("logo")

        val importacoes = Tab(
            VaadinIcon.LINE_BAR_CHART.create(),
            RouterLink("Importações", ImportTransactionsView::class.java)
        )
        val usuarios = Tab(
            VaadinIcon.USERS.create(),
            RouterLink("Usuários", UsersView::class.java)
        )

        val tabs = Tabs(importacoes, usuarios)
        tabs.style.set("margin", "auto")

        val logoutButton = Button("Logout", VaadinIcon.EXIT.create())
        logoutButton.addClickListener {
            securityService.logout()
        }
        logoutButton.style
            .set("right", "var(--lumo-space-l)")
            .set("margin", "0")
            .set("position", "absolute")

        addToNavbar(logo, tabs, logoutButton)
    }
}