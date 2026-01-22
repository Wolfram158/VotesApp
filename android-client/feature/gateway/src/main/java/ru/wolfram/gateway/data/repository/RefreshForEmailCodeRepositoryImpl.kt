package ru.wolfram.gateway.data.repository

import ru.wolfram.common.data.network.dto.UserDto2
import ru.wolfram.common.data.network.service.ApiService
import ru.wolfram.common.domain.storage.LocalDataStorage
import ru.wolfram.gateway.domain.model.RefreshForEmailCodeContainer
import ru.wolfram.gateway.domain.repository.RefreshForEmailCodeRepository
import javax.inject.Inject

internal class RefreshForEmailCodeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val localDataStorage: LocalDataStorage
) : RefreshForEmailCodeRepository {
    override suspend fun refreshForEmailCode(container: RefreshForEmailCodeContainer): Result<Unit> {
        return try {
            val response = apiService.refreshForEmailCode(
                UserDto2(
                    username = container.username,
                    password = container.password
                )
            )
            if (response.code() != 200) {
                throw RuntimeException("Exception occurred when getting email code for refreshing!")
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkIfNeedEmailCode(username: String): Result<Boolean> {
        return try {
            val refreshToken =
                localDataStorage.readRefreshTokenPreferences()?.token
                    ?: throw RuntimeException("Refresh token must be non-nullable!")
            val response = apiService.checkIfNeedEmailCode(username, refreshToken)
            if (response.code() == 404) {
                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}