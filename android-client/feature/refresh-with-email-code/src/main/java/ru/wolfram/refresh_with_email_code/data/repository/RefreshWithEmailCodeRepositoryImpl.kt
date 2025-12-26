package ru.wolfram.refresh_with_email_code.data.repository

import ru.wolfram.common.data.network.service.ApiService
import ru.wolfram.common.data.security.AccessTokenPreferences
import ru.wolfram.common.data.security.RefreshTokenPreferences
import ru.wolfram.common.data.security.UsernamePreferences
import ru.wolfram.common.domain.storage.LocalDataStorage
import ru.wolfram.refresh_with_email_code.data.mapper.toRefreshWithEmailCodeContainerDto
import ru.wolfram.refresh_with_email_code.domain.model.RefreshWithEmailCodeContainer
import ru.wolfram.refresh_with_email_code.domain.repository.RefreshWithEmailCodeRepository
import javax.inject.Inject

internal class RefreshWithEmailCodeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val localDataStorage: LocalDataStorage
) : RefreshWithEmailCodeRepository {
    override suspend fun refreshWithEmailCode(container: RefreshWithEmailCodeContainer): Result<Unit> {
        return try {
            val tokens = apiService.refreshWithEmailCode(
                refreshWithEmailCodeContainerDto = container.copy(username = container.username)
                    .toRefreshWithEmailCodeContainerDto()
            )
            localDataStorage.writeUsernamePreferences(UsernamePreferences(container.username))
            localDataStorage.writeAccessTokenPreferences(AccessTokenPreferences(tokens.token))
            localDataStorage.writeRefreshTokenPreferences(RefreshTokenPreferences(tokens.refreshToken))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}