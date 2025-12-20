package ru.wolfram.vote.domain.gateway.repository

import ru.wolfram.vote.domain.gateway.model.RefreshForEmailCodeContainer

interface RefreshForEmailCodeRepository {
    suspend fun refreshForEmailCode(container: RefreshForEmailCodeContainer): Result<Unit>

    suspend fun checkIfNeedEmailCode(username: String): Result<Boolean>
}