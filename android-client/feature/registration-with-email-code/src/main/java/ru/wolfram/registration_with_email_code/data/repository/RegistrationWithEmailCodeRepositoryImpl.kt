package ru.wolfram.registration_with_email_code.data.repository

import androidx.datastore.core.DataStore
import ru.wolfram.common.data.network.service.ApiService
import ru.wolfram.common.data.security.AccessTokenPreferences
import ru.wolfram.common.data.security.RefreshTokenPreferences
import ru.wolfram.registration_with_email_code.data.mapper.toRegistrationWithEmailCodeContainerDto
import ru.wolfram.registration_with_email_code.domain.model.RegistrationWithEmailCodeContainer
import ru.wolfram.registration_with_email_code.domain.repository.RegistrationWithEmailCodeRepository
import javax.inject.Inject

internal class RegistrationWithEmailCodeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val accessTokenStore: DataStore<AccessTokenPreferences>,
    private val refreshTokenStore: DataStore<RefreshTokenPreferences>,
) : RegistrationWithEmailCodeRepository {
    override suspend fun registerWithEmailCode(container: RegistrationWithEmailCodeContainer): Result<Unit> {
        return try {
            val username = container.username
            val tokens = apiService.registerWithEmailCode(
                registrationWithEmailCodeContainerDto = container.copy(username = username)
                    .toRegistrationWithEmailCodeContainerDto()
            )
            accessTokenStore.updateData {
                AccessTokenPreferences(tokens.token)
            }
            refreshTokenStore.updateData {
                RefreshTokenPreferences(tokens.refreshToken)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}