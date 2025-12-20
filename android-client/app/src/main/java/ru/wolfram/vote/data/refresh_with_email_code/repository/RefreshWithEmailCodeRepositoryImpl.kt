package ru.wolfram.vote.data.refresh_with_email_code.repository

import androidx.datastore.core.DataStore
import ru.wolfram.vote.data.network.dto.toRefreshWithEmailCodeContainerDto
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.data.security.AccessTokenPreferences
import ru.wolfram.vote.data.security.RefreshTokenPreferences
import ru.wolfram.vote.data.security.UsernamePreferences
import ru.wolfram.vote.domain.refresh_with_email_code.model.RefreshWithEmailCodeContainer
import ru.wolfram.vote.domain.refresh_with_email_code.repository.RefreshWithEmailCodeRepository
import javax.inject.Inject

class RefreshWithEmailCodeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val usernameStore: DataStore<UsernamePreferences>,
    private val accessTokenStore: DataStore<AccessTokenPreferences>,
    private val refreshTokenStore: DataStore<RefreshTokenPreferences>,
) : RefreshWithEmailCodeRepository {
    override suspend fun refreshWithEmailCode(container: RefreshWithEmailCodeContainer): Result<Unit> {
        return try {
            val tokens = apiService.refreshWithEmailCode(
                refreshWithEmailCodeContainerDto = container.copy(username = container.username)
                    .toRefreshWithEmailCodeContainerDto()
            )
            usernameStore.updateData {
                UsernamePreferences(container.username)
            }
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