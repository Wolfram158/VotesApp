package ru.wolfram.refresh_with_email_code.domain.repository

import ru.wolfram.refresh_with_email_code.domain.model.RefreshWithEmailCodeContainer

interface RefreshWithEmailCodeRepository {
    suspend fun refreshWithEmailCode(container: RefreshWithEmailCodeContainer): Result<Unit>
}