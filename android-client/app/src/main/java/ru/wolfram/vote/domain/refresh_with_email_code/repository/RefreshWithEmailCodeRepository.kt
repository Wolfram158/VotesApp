package ru.wolfram.vote.domain.refresh_with_email_code.repository

import ru.wolfram.vote.domain.refresh_with_email_code.model.RefreshWithEmailCodeContainer

interface RefreshWithEmailCodeRepository {
    suspend fun refreshWithEmailCode(container: RefreshWithEmailCodeContainer): Result<Unit>
}