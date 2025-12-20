package ru.wolfram.vote.presentation

sealed interface RegistrationForEmailCodeState {
    object Initial : RegistrationForEmailCodeState
    object Success : RegistrationForEmailCodeState
    object Failure : RegistrationForEmailCodeState
}