package ru.wolfram.email.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.wolfram.email.service.EmailService

@RestController
@RequestMapping("/api/v1/email")
class EmailController(
    private val emailService: EmailService
) {
    @PostMapping("/send-email-code")
    fun sendEmailCode(
        @RequestParam(name = "email") email: String,
        @RequestParam(name = "username") username: String
    ): ResponseEntity<String> {
        emailService.sendEmailCode(username, email)
        return ResponseEntity.ok("Email code sent!")
    }

    @GetMapping("/get-encoded-email-code-by-username")
    fun getEncodedEmailCodeByUsername(
        @RequestParam(name = "username") username: String
    ): ResponseEntity<String> {
        return emailService.getEncodedEmailCodeByUsername(username)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }
}
