package ru.wolfram.vote.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.wolfram.vote.dto.*
import ru.wolfram.vote.service.AuthService

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {
    @Autowired
    private lateinit var service: AuthService

    @PostMapping("/register-for-email-code")
    fun registerForEmailCode(@RequestBody userDto: UserDto): ResponseEntity<String> {
        val result = service.registerForEmailCode(userDto)
        if (result !is RegistrationForEmailCodeState.Success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
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
        if (result !is RegistrationWithEmailCodeState.Success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
        return ResponseEntity.ok(result)
    }

    @PostMapping("/refresh-token")
    fun refreshToken(
        @RequestParam refreshToken: String
    ): ResponseEntity<RefreshTokenResult.Success> {
        val result =
            service.refreshToken(refreshToken)
        if (result !is RefreshTokenResult.Success) {
            println(result)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
        return ResponseEntity.ok(result)
    }

    @PostMapping("/refresh-for-email-code")
    fun refreshForEmail(
        @RequestParam username: String
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