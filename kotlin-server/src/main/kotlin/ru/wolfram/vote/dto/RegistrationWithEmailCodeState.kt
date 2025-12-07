package ru.wolfram.vote.dto

sealed interface RegistrationWithEmailCodeState {
    data object UserAlreadyExists : RegistrationForEmailCodeState
    data object CodeNotFound : RegistrationWithEmailCodeState
    data object IncorrectCode : RegistrationWithEmailCodeState
    data class Success(
        val token: String,
        val refreshToken: String
    ) : RegistrationWithEmailCodeState
}