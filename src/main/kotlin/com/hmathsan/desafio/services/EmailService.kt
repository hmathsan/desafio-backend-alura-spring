package com.hmathsan.desafio.services

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailService(
    private val mailSender: JavaMailSender
) {

    fun sendEmailWithPassword(to: String, password: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.setSubject("A sua senha do Sistema de Análises Financeiras")
        message.setText(password)

        mailSender.send(message)
    }
}