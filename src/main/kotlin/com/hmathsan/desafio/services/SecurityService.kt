package com.hmathsan.desafio.services

import com.hmathsan.desafio.entities.User
import com.vaadin.flow.component.UI
import com.vaadin.flow.server.VaadinServletRequest
import org.springframework.security.authentication.jaas.SecurityContextLoginModule
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Component
import javax.servlet.ServletException

@Component
class SecurityService {
    val logoutSuccessUrl = "/"

    fun getAuthenticatedUser() : User? {
        val context = SecurityContextHolder.getContext()
        val principal = context.authentication.principal

        if (principal is User) {
            return context.authentication.principal as User
        }

        return null
    }

    fun logout() {
        UI.getCurrent().page.setLocation(logoutSuccessUrl)
        val logoutHandler = SecurityContextLogoutHandler()
        logoutHandler.logout(
            VaadinServletRequest.getCurrent().httpServletRequest, null, null
        )
    }
}