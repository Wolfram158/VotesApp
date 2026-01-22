package ru.wolfram.auth.dto

sealed interface RefreshForEmailCodeState {
    data object UserNotFound : RefreshForEmailCodeState

    data object EmailServiceException : RefreshForEmailCodeState

    data object ImpossibleState : RefreshForEmailCodeState

    data object WrongPassword : RefreshForEmailCodeState

    data object Success : RefreshForEmailCodeState
}