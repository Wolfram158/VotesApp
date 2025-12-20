package ru.wolfram.vote.presentation

sealed interface RefreshForEmailCodeState {
    object Initial : RefreshForEmailCodeState
    object Success : RefreshForEmailCodeState
    object Failure : RefreshForEmailCodeState
}