package ru.wolfram.common.domain.storage

import ru.wolfram.common.data.security.AccessTokenPreferences
import ru.wolfram.common.data.security.EmailPreferences
import ru.wolfram.common.data.security.RefreshTokenPreferences
import ru.wolfram.common.data.security.UsernamePreferences

interface LocalDataStorage {
    suspend fun readEmailPreferences(): EmailPreferences?

    suspend fun writeEmailPreferences(emailPreferences: EmailPreferences)

    suspend fun readUsernamePreferences(): UsernamePreferences?

    suspend fun writeUsernamePreferences(usernamePreferences: UsernamePreferences)

    suspend fun readAccessTokenPreferences(): AccessTokenPreferences?

    suspend fun writeAccessTokenPreferences(accessTokenPreferences: AccessTokenPreferences)

    suspend fun readRefreshTokenPreferences(): RefreshTokenPreferences?

    suspend fun writeRefreshTokenPreferences(refreshTokenPreferences: RefreshTokenPreferences)
}