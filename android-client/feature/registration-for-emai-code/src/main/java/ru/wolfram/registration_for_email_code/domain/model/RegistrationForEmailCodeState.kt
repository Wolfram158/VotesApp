package ru.wolfram.registration_for_email_code.domain.model

internal sealed interface RegistrationForEmailCodeState {
    object Initial : RegistrationForEmailCodeState
    object Success : RegistrationForEmailCodeState
    object Failure : RegistrationForEmailCodeState
}