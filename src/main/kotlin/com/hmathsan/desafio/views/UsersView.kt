package com.hmathsan.desafio.views

import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import javax.annotation.security.PermitAll

@Route("/users", layout = MainLayoutView::class)
@PermitAll
class UsersView : VerticalLayout() {
    init {
        val header = H1("Users")
        add(header)
    }
}