package ru.wolfram.email.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(
    private val mailSender: JavaMailSender
) {
    @Value($$"${spring.mail.username}")
    private val username: String? = null

    fun send(emailTo: String, subject: String, message: String) {
        val mailMessage = SimpleMailMessage()
        mailMessage.from = username
        mailMessage.setTo(emailTo)
        mailMessage.subject = subject
        mailMessage.text = message
        mailSender.send(mailMessage)
    }
}