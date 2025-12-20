package ru.wolfram.vote.presentation

sealed interface RegistrationWithEmailCodeState {
    object Initial : RegistrationWithEmailCodeState
    object Success : RegistrationWithEmailCodeState
    object Failure : RegistrationWithEmailCodeState
}