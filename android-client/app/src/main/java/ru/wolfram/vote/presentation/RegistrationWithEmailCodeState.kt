package ru.wolfram.vote.presentation

sealed interface RegistrationWithEmailCodeState {
    object Success : RegistrationWithEmailCodeState
    object Failure : RegistrationWithEmailCodeState
}