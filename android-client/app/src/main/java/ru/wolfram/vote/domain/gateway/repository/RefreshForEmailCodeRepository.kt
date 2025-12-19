package ru.wolfram.vote.domain.gateway.repository

import ru.wolfram.vote.domain.gateway.model.RefreshForEmailCodeContainer

interface RefreshForEmailCodeRepository {
    suspend fun refreshForEmailCode(container: RefreshForEmailCodeContainer)
}