package ru.wolfram.vote.data.registration_with_email_code.repository

import androidx.datastore.core.DataStore
import ru.wolfram.vote.data.network.dto.toRegistrationWithEmailCodeContainerDto
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.data.security.AccessTokenPreferences
import ru.wolfram.vote.data.security.RefreshTokenPreferences
import ru.wolfram.vote.domain.registration_with_email_code.model.RegistrationWithEmailCodeContainer
import ru.wolfram.vote.domain.registration_with_email_code.repository.RegistrationWithEmailCodeRepository
import javax.inject.Inject

class RegistrationWithEmailCodeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val accessTokenStore: DataStore<AccessTokenPreferences>,
    private val refreshTokenStore: DataStore<RefreshTokenPreferences>,
) : RegistrationWithEmailCodeRepository {
    override suspend fun registerWithEmailCode(container: RegistrationWithEmailCodeContainer) {
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
    }
}