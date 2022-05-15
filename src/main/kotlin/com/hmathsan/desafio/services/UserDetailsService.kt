package com.hmathsan.desafio.services

import com.hmathsan.desafio.model.MyUserPrincipal
import com.hmathsan.desafio.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    @Autowired
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
        if (user.isEmpty) {
            throw UsernameNotFoundException(username)
        }

        return MyUserPrincipal(user.get())
    }
}