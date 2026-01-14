package ru.wolfram.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserDto(
    @Size(min = 1, max = 32)
    @Pattern(regexp = "^\\S+$")
    val username: String,
    @Email
    @NotBlank
    @Size(max = 256)
    val email: String
)
