package ru.wolfram.email.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.wolfram.email.component.SecurityUtils
import ru.wolfram.email.entity.Session
import ru.wolfram.email.repository.SessionRepository
import kotlin.jvm.optionals.getOrNull

@Service
class EmailService(
    private val mailService: MailService,
    private val sessionRepository: SessionRepository,
    private val passwordEncoder: PasswordEncoder,
    private val securityUtils: SecurityUtils,
    @Value($$"${mail.codeLength}") private val codeLength: Int,
    @Value($$"${mail.codeExpirationSeconds}") private val codeExpirationSeconds: Long
) {
    fun sendEmailCode(username: String, email: String) {
        val code = securityUtils.randomString(codeLength)
        val encoded = passwordEncoder.encode(code)
        sessionRepository.save(
            Session(
                username = username,
                code = encoded!!,
                expirationInSeconds = codeExpirationSeconds
            )
        )
        mailService.send(email, "Access code", "Your code: $code")
    }

    fun getEncodedEmailCodeByUsername(username: String): String? {
        val code = sessionRepository.findById(username).getOrNull()?.code
        println("Code: $code")
        return code
    }
}