package com.hmathsan.desafio.config

import com.hmathsan.desafio.services.CustomAuthenticationProvider
import com.hmathsan.desafio.views.LoginView
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val customAuthenticationProvider: CustomAuthenticationProvider
) : VaadinWebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.rememberMe().alwaysRemember(false)
        super.configure(http)
        setLoginView(http, LoginView::class.java)
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/images/**"
        )

        super.configure(web)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(customAuthenticationProvider)
    }

    @Bean
    override fun userDetailsServiceBean(): UserDetailsService {
        return super.userDetailsServiceBean()
    }
}