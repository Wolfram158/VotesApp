package ru.wolfram.registration_with_email_code.domain.model

internal sealed interface RegistrationWithEmailCodeState {
    object Initial : RegistrationWithEmailCodeState
    object Success : RegistrationWithEmailCodeState
    object Failure : RegistrationWithEmailCodeState
}