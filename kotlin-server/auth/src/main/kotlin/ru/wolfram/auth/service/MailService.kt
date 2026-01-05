package ru.wolfram.auth.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class MailService(
    private val restTemplate: RestTemplate,
    @Value($$"${email.url}") private val emailBaseUrl: String
) {
    fun sendEmailCode(username: String, email: String): ResponseEntity<String> {
        val url = UriComponentsBuilder
            .fromUriString("$emailBaseUrl/send-email-code")
            .queryParam("username", username)
            .queryParam("email", email)
            .toUriString()
        return restTemplate.postForEntity(url, null, String::class.java)
    }

    fun getEncodedEmailCodeByUsername(username: String): ResponseEntity<String> {
        val url = UriComponentsBuilder
            .fromUriString("$emailBaseUrl/get-encoded-email-code-by-username")
            .queryParam("username", username)
            .toUriString()
        return restTemplate.getForEntity(url, String::class.java)
    }
}