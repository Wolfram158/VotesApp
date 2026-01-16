package ru.wolfram.auth.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.wolfram.auth.constants.Constants
import ru.wolfram.auth.dto.*
import ru.wolfram.auth.service.AuthService

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val service: AuthService
) {
    @PostMapping("/register-for-email-code")
    fun registerForEmailCode(
        @Valid @RequestBody userDto: UserDto
    ): ResponseEntity<String> {
        return when (service.registerForEmailCode(userDto)) {
            is RegistrationForEmailCodeState.UserAlreadyExists -> {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }

            is RegistrationForEmailCodeState.EmailServiceException -> {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }

            is RegistrationForEmailCodeState.Success -> {
                ResponseEntity.ok("Code sent")
            }
        }
    }

    @PostMapping("/register-with-email-code")
    fun registerWithEmailCode(
        @Valid @RequestBody registrationWithEmailCodeDto: RegistrationWithEmailCodeDto
    ): ResponseEntity<RegistrationWithEmailCodeState.Success> {
        val result =
            service.registerWithEmailCode(
                username = registrationWithEmailCodeDto.username,
                email = registrationWithEmailCodeDto.email,
                code = registrationWithEmailCodeDto.code,
                password = registrationWithEmailCodeDto.password
            )
        return when (result) {
            RegistrationWithEmailCodeState.EmailServiceException -> {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }

            RegistrationWithEmailCodeState.IncorrectCode, RegistrationWithEmailCodeState.UsernameNotFound -> {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }

            is RegistrationWithEmailCodeState.Success -> {
                ResponseEntity.ok(result)
            }
        }
    }

    @PostMapping("/refresh-token")
    fun refreshToken(
        @RequestHeader(name = Constants.AUTHORIZATION_HEADER_KEY) refreshToken: String
    ): ResponseEntity<RefreshTokenResult.Success> {
        return when (val result = service.refreshToken(refreshToken)) {
            RefreshTokenResult.IncorrectRefreshToken, RefreshTokenResult.RefreshTokenNotFound -> {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }

            is RefreshTokenResult.Success -> {
                ResponseEntity.ok(result)
            }
        }
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