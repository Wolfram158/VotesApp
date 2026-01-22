package ru.wolfram.auth.dto

sealed interface RegistrationWithEmailCodeState {
    data object IncorrectCode : RegistrationWithEmailCodeState

    data object EmailServiceException : RegistrationWithEmailCodeState

    data object UsernameNotFound : RegistrationWithEmailCodeState

    data class Success(
        val token: String,
        val refreshToken: String
    ) : RegistrationWithEmailCodeState
}