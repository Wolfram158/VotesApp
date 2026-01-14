package ru.wolfram.auth.controller

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.wolfram.auth.constants.Constants
import ru.wolfram.auth.dto.*
import ru.wolfram.auth.service.AuthService

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {
    @Autowired
    private lateinit var service: AuthService

    @PostMapping("/register-for-email-code")
    fun registerForEmailCode(
        @Valid @RequestBody userDto: UserDto
    ): ResponseEntity<String> {
        val result = service.registerForEmailCode(userDto)
        if (result is RegistrationForEmailCodeState.UserAlreadyExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        } else if (result is RegistrationForEmailCodeState.EmailServiceException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
        return ResponseEntity.ok("Code sent")
    }

    @PostMapping("/register-with-email-code")
    fun registerWithEmailCode(
        @RequestBody registrationWithEmailCodeDto: RegistrationWithEmailCodeDto
    ): ResponseEntity<RegistrationWithEmailCodeState.Success> {
        val result =
            service.registerWithEmailCode(
                username = registrationWithEmailCodeDto.username,
                email = registrationWithEmailCodeDto.email,
                code = registrationWithEmailCodeDto.code,
                password = registrationWithEmailCodeDto.password
            )
        println("Result: $result")
        if (result !is RegistrationWithEmailCodeState.Success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
        return ResponseEntity.ok(result)
    }

    @PostMapping("/refresh-token")
    fun refreshToken(
        @RequestHeader(name = Constants.AUTHORIZATION_HEADER_KEY) refreshToken: String
    ): ResponseEntity<RefreshTokenResult.Success> {
        val result =
            service.refreshToken(refreshToken)
        if (result !is RefreshTokenResult.Success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
        return ResponseEntity.ok(result)
    }

    @PostMapping("/refresh-for-email-code")
    fun refreshForEmail(
        @RequestParam(name = Constants.USERNAME) username: String
    ) {
        service.refreshForEmailCode(username)
    }

    @PostMapping("/refresh-with-email-code")
    fun refreshWithEmailCode(
        @RequestBody refreshWithEmailCodeDto: RefreshWithEmailCodeDto
    ): ResponseEntity<RegistrationWithEmailCodeState.Success> {
        val result =
            service.refreshWithEmailCode(
                username = refreshWithEmailCodeDto.username,
                code = refreshWithEmailCodeDto.code,
            )
        if (result !is RegistrationWithEmailCodeState.Success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
        return ResponseEntity.ok(result)
    }
}