package ru.wolfram.auth.dto

sealed interface RegistrationForEmailCodeState {
    data object EmailServiceException : RegistrationForEmailCodeState

    data object UserAlreadyExists : RegistrationForEmailCodeState

    data object Success : RegistrationForEmailCodeState
}