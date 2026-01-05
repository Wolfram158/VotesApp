package ru.wolfram.auth.dto

sealed interface RegistrationForEmailCodeState {
    data object UserAlreadyExists : RegistrationForEmailCodeState

    data object Success : RegistrationForEmailCodeState
}