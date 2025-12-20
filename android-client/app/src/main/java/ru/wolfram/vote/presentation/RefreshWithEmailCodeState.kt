package ru.wolfram.vote.presentation

sealed interface RefreshWithEmailCodeState {
    object Initial : RefreshWithEmailCodeState
    object Success : RefreshWithEmailCodeState
    object Failure : RefreshWithEmailCodeState
}