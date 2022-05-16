package com.hmathsan.desafio.useCases

import com.hmathsan.desafio.entities.User
import com.hmathsan.desafio.model.Roles
import com.hmathsan.desafio.repositories.UserRepository
import com.hmathsan.desafio.services.EmailService
import com.hmathsan.desafio.services.SecurityService
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsersUseCase(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val emailService: EmailService
) {
    fun editUserListener(user: User) {
        TODO()
    }

    fun removeUserListener(user: User) {
        user.isDeleted = true
        userRepository.save(user)
    }

    fun createNewUserListener(name: String, email: String) {
        val possibleUser = userRepository.findByEmail(email)

        if (possibleUser.isPresent) {
            val notification = Notification.show("Email já cadastrado")
            notification.position = Notification.Position.BOTTOM_CENTER
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR)

            return
        }

        val passwordList = (0..10).shuffled().take(6)
        val password = passwordList.joinToString("")

        emailService.sendEmailWithPassword(email, password)

        val encryptedPassword = BCryptPasswordEncoder().encode(password)
        val newUser = User(null, name, email, encryptedPassword, Roles.USER)
        userRepository.save(newUser)
    }
}