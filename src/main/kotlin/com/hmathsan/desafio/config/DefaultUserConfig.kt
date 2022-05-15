package com.hmathsan.desafio.config

import com.hmathsan.desafio.entities.User
import com.hmathsan.desafio.model.Roles
import com.hmathsan.desafio.repositories.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories
class DefaultUserConfig(
    @Value("\${default.user.email}")
    private val defaultUserEmail: String,

    @Value("\${default.user.name}")
    private val defaultUserName: String,

    @Value("\${default.user.password}")
    private val defaultUserPassword: String,

    private val userRepository: UserRepository
) {

    @Bean
    @Transactional
    fun createDefaultUser() {
        val defaultUser = userRepository.findByEmail(defaultUserEmail)

        if (defaultUser.isEmpty) {
            val user = User(
                null,
                defaultUserName,
                defaultUserEmail,
                BCryptPasswordEncoder().encode(defaultUserPassword).toString(),
                Roles.ADMIN
            )

            userRepository.save(user)
        }
    }
}