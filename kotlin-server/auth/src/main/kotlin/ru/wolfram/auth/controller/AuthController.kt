package ru.wolfram.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.wolfram.auth.constants.Constants
import ru.wolfram.auth.dto.*
import ru.wolfram.auth.service.AuthService

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Rest API for auth service")
class AuthController(
    private val service: AuthService
) {
    @Operation(
        summary = "Sends code to email address",
        description = "Returns 200 if successful"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(
                responseCode = "400",
                description = "User already exists"
            ),
            ApiResponse(
                responseCode = "500", description = "Email service couldn't cope with sending code or " +
                        "something else (what?)"
            )
        ]
    )
    @PostMapping("/register-for-email-code")
    fun registerForEmailCode(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Information necessary to send code to email address",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserDto::class)
                )
            ]
        )
        @Valid
        @RequestBody userDto: UserDto
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

    @Operation(
        summary = "If code (sent to email address) is appropriate, then returns new access and refresh token",
        description = "Returns 200 if successful"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(
                responseCode = "400",
                description = "User not found in email service or code is bad or something else (what?)"
            ),
            ApiResponse(
                responseCode = "500", description = "Email service couldn't cope with sending code or " +
                        "something else (what?)"
            )
        ]
    )
    @PostMapping("/register-with-email-code")
    fun registerWithEmailCode(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Information necessary to send code to email address",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RegistrationWithEmailCodeDto::class)
                )
            ]
        )
        @Valid
        @RequestBody registrationWithEmailCodeDto: RegistrationWithEmailCodeDto
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

    @Operation(
        summary = "Gives new access token and refresh token",
        description = "Returns 200 if successful"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "400", description = "Old refresh token (provided through header) is bad")
        ]
    )
    @PostMapping("/refresh-token")
    fun refreshToken(
        @Parameter(
            name = Constants.AUTHORIZATION_HEADER_KEY,
            description = "Refresh token for getting new access token and refresh token",
            required = true,
            schema = Schema(
                type = "string",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                        "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ." +
                        "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            ),
            `in` = ParameterIn.HEADER
        )
        @RequestHeader(name = Constants.AUTHORIZATION_HEADER_KEY)
        refreshToken: String
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

    @Operation(
        summary = "Sends code to email address",
        description = "Returns 200 if successful"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(
                responseCode = "400",
                description = "User not found or password is bad or something else (what?)"
            ),
            ApiResponse(
                responseCode = "500", description = "Email service couldn't cope with sending code or " +
                        "something else (what?)"
            )
        ]
    )
    @PostMapping("/refresh-for-email-code")
    fun refreshForEmail(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Information necessary to send code to email address",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserDto2::class)
                )
            ]
        )
        @RequestBody
        userDto: UserDto2
    ): ResponseEntity<String> {
        return when (service.refreshForEmailCode(userDto)) {
            RefreshForEmailCodeState.EmailServiceException -> {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }

            RefreshForEmailCodeState.Success -> {
                ResponseEntity.ok("Code sent")
            }

            RefreshForEmailCodeState.UserNotFound,
            RefreshForEmailCodeState.ImpossibleState,
            RefreshForEmailCodeState.WrongPassword -> {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
        }
    }

    @Operation(
        summary = "If code (sent to email address) is appropriate, then returns new access and refresh token",
        description = "Returns 200 if successful"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(
                responseCode = "400",
                description = "User not found or code is bad or something else (what?)"
            ),
            ApiResponse(
                responseCode = "500", description = "Email service couldn't cope with sending code or" +
                        "something else (what?)"
            )
        ]
    )
    @PostMapping("/refresh-with-email-code")
    fun refreshWithEmailCode(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RefreshWithEmailCodeDto::class)
                )
            ]
        )
        @RequestBody refreshWithEmailCodeDto: RefreshWithEmailCodeDto
    ): ResponseEntity<RefreshWithEmailCodeState.Success> {
        val result =
            service.refreshWithEmailCode(
                username = refreshWithEmailCodeDto.username,
                code = refreshWithEmailCodeDto.code,
            )
        return when (result) {
            RefreshWithEmailCodeState.EmailServiceException -> {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }

            RefreshWithEmailCodeState.IncorrectCode,
            RefreshWithEmailCodeState.UsernameNotFound -> {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }

            is RefreshWithEmailCodeState.Success -> {
                ResponseEntity.ok(result)
            }
        }
    }
}