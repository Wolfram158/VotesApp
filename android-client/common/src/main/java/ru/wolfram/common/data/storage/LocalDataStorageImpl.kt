package ru.wolfram.common.data.storage

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.firstOrNull
import ru.wolfram.common.data.security.AccessTokenPreferences
import ru.wolfram.common.data.security.EmailPreferences
import ru.wolfram.common.data.security.RefreshTokenPreferences
import ru.wolfram.common.data.security.UsernamePreferences
import ru.wolfram.common.di.AppScope
import ru.wolfram.common.domain.storage.LocalDataStorage

@AppScope
internal class LocalDataStorageImpl(
    private val accessTokenStore: DataStore<AccessTokenPreferences>,
    private val refreshTokenStore: DataStore<RefreshTokenPreferences>,
    private val usernameStore: DataStore<UsernamePreferences>,
    private val emailStore: DataStore<EmailPreferences>
) : LocalDataStorage {
    override suspend fun readEmailPreferences(): EmailPreferences? {
        return emailStore.data.firstOrNull()
    }

    override suspend fun writeEmailPreferences(emailPreferences: EmailPreferences) {
        emailStore.updateData {
            emailPreferences
        }
    }

    override suspend fun readUsernamePreferences(): UsernamePreferences? {
        return usernameStore.data.firstOrNull()
    }

    override suspend fun writeUsernamePreferences(usernamePreferences: UsernamePreferences) {
        usernameStore.updateData {
            usernamePreferences
        }
    }

    override suspend fun readAccessTokenPreferences(): AccessTokenPreferences? {
        return accessTokenStore.data.firstOrNull()
    }

    override suspend fun writeAccessTokenPreferences(accessTokenPreferences: AccessTokenPreferences) {
        accessTokenStore.updateData {
            accessTokenPreferences
        }
    }

    override suspend fun readRefreshTokenPreferences(): RefreshTokenPreferences? {
        return refreshTokenStore.data.firstOrNull()
    }

    override suspend fun writeRefreshTokenPreferences(refreshTokenPreferences: RefreshTokenPreferences) {
        refreshTokenStore.updateData {
            refreshTokenPreferences
        }
    }
}