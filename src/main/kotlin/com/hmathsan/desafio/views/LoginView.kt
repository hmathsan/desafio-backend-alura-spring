package com.hmathsan.desafio.views

import com.hmathsan.desafio.services.SecurityService
import com.vaadin.flow.component.ComponentEventListener
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.login.AbstractLogin
import com.vaadin.flow.component.login.LoginForm
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.*
import javax.annotation.security.PermitAll

@Route("/login")
@PageTitle("Login")
@PermitAll
class LoginView(
    private val securityService: SecurityService
) : VerticalLayout(), BeforeEnterObserver, BeforeLeaveObserver {
    private val loginForm = LoginForm()

    private val loginSuccessUrl = "/import_transaction"

    init {
        addClassName("login-view")
        setSizeFull()

        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER

        loginForm.action = "login"

        add(H1("SISTEMA DE ANÁLISE DE TRANSAÇÕES FINANCEIRAS"), loginForm)
    }

    override fun beforeEnter(beforeEnterEvent: BeforeEnterEvent) {
        if (beforeEnterEvent.location.queryParameters.parameters.containsKey("error")) {
            loginForm.isError = true
        }
    }

    override fun beforeLeave(event: BeforeLeaveEvent) {
        val authenticatedUser = securityService.getAuthenticatedUser()

        event.rerouteTo(loginSuccessUrl)
    }

}