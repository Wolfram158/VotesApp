package ru.wolfram.gateway.domain.repository

import ru.wolfram.gateway.domain.model.RefreshForEmailCodeContainer

interface RefreshForEmailCodeRepository {
    suspend fun refreshForEmailCode(container: RefreshForEmailCodeContainer): Result<Unit>

    suspend fun checkIfNeedEmailCode(username: String): Result<Boolean>
}