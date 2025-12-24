package ru.wolfram.gateway.domain.model

sealed interface RefreshForEmailCodeState {
    object Initial : RefreshForEmailCodeState
    object Success : RefreshForEmailCodeState
    object Failure : RefreshForEmailCodeState
}