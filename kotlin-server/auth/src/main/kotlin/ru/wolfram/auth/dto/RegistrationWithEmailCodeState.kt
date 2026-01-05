package ru.wolfram.auth.dto

sealed interface RegistrationWithEmailCodeState {
    data object UserAlreadyExists : RegistrationForEmailCodeState
    data object CodeNotFound : RegistrationWithEmailCodeState
    data object IncorrectCode : RegistrationWithEmailCodeState

    data object Failure : RegistrationWithEmailCodeState

    data class Success(
        val token: String,
        val refreshToken: String
    ) : RegistrationWithEmailCodeState
}