package ru.wolfram.vote.dto

sealed interface CheckIfNeedEmailCodeState {
    object UserNotFound : CheckIfNeedEmailCodeState
    object Yes : CheckIfNeedEmailCodeState
    object No : CheckIfNeedEmailCodeState
}