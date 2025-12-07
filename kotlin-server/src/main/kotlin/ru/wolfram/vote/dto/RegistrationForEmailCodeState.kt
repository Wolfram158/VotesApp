package ru.wolfram.vote.dto

sealed interface RegistrationForEmailCodeState {
    data object UserAlreadyExists : RegistrationForEmailCodeState

    data object Success : RegistrationForEmailCodeState
}