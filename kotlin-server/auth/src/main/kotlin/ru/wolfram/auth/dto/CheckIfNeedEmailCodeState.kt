package ru.wolfram.auth.dto

sealed interface CheckIfNeedEmailCodeState {
    object Need : CheckIfNeedEmailCodeState
    object NoNeed : CheckIfNeedEmailCodeState
}