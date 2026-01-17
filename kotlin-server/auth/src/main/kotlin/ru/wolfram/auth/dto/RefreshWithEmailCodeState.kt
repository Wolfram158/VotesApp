package ru.wolfram.auth.dto

sealed interface RefreshWithEmailCodeState {
    data object IncorrectCode : RefreshWithEmailCodeState

    data object EmailServiceException : RefreshWithEmailCodeState

    data object UsernameNotFound : RefreshWithEmailCodeState

    data class Success(
        val token: String,
        val refreshToken: String
    ) : RefreshWithEmailCodeState
}