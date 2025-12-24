package ru.wolfram.refresh_with_email_code.domain.model

internal sealed interface RefreshWithEmailCodeState {
    object Initial : RefreshWithEmailCodeState
    object Success : RefreshWithEmailCodeState
    object Failure : RefreshWithEmailCodeState
}