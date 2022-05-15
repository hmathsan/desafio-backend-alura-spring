package com.hmathsan.desafio.services

import com.hmathsan.desafio.repositories.UserRepository
import com.vaadin.flow.component.UI
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val userRepository: UserRepository
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val email = authentication.name
        val password = authentication.credentials.toString()

        val user = userRepository.findByEmail(email)

        if (user.isEmpty) {
            throw BadCredentialsException("Authentication failed")
        } else {
            if (BCryptPasswordEncoder().matches(password, user.get().password)) {
                return UsernamePasswordAuthenticationToken(user.get(), password, mutableListOf())
            } else {
                throw BadCredentialsException("Authentication failed")
            }
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}