package ru.wolfram.common.data.storage

import ru.wolfram.common.data.security.AccessTokenPreferences
import ru.wolfram.common.data.security.EmailPreferences
import ru.wolfram.common.data.security.RefreshTokenPreferences
import ru.wolfram.common.data.security.UsernamePreferences
import ru.wolfram.common.di.AppScope
import ru.wolfram.common.domain.storage.LocalDataStorage

@AppScope
class LocalDataStorageTestImpl : LocalDataStorage {
    private lateinit var usernamePreferences: UsernamePreferences
    private lateinit var emailPreferences: EmailPreferences
    private lateinit var accessTokenPreferences: AccessTokenPreferences
    private lateinit var refreshTokenPreferences: RefreshTokenPreferences

    constructor (
        usernamePreferences: UsernamePreferences? = UsernamePreferences("John"),
        emailPreferences: EmailPreferences? = EmailPreferences("john@gmail.com"),
        accessTokenPreferences: AccessTokenPreferences? = AccessTokenPreferences("1234"),
        refreshTokenPreferences: RefreshTokenPreferences? = RefreshTokenPreferences("2345")
    ) {
        if (usernamePreferences != null) {
            this.usernamePreferences = usernamePreferences
        }
        if (emailPreferences != null) {
            this.emailPreferences = emailPreferences
        }
        if (accessTokenPreferences != null) {
            this.accessTokenPreferences = accessTokenPreferences
        }
        if (refreshTokenPreferences != null) {
            this.refreshTokenPreferences = refreshTokenPreferences
        }
    }

    override suspend fun readEmailPreferences(): EmailPreferences? {
        return if (::emailPreferences.isInitialized) {
            emailPreferences
        } else {
            null
        }
    }

    override suspend fun writeEmailPreferences(emailPreferences: EmailPreferences) {
        this.emailPreferences = emailPreferences
    }

    override suspend fun readUsernamePreferences(): UsernamePreferences? {
        return if (::usernamePreferences.isInitialized) {
            usernamePreferences
        } else {
            null
        }
    }

    override suspend fun writeUsernamePreferences(usernamePreferences: UsernamePreferences) {
        this.usernamePreferences = usernamePreferences
    }

    override suspend fun readAccessTokenPreferences(): AccessTokenPreferences? {
        return if (::accessTokenPreferences.isInitialized) {
            accessTokenPreferences
        } else {
            accessTokenPreferences
        }
    }

    override suspend fun writeAccessTokenPreferences(accessTokenPreferences: AccessTokenPreferences) {
        this.accessTokenPreferences = accessTokenPreferences
    }

    override suspend fun readRefreshTokenPreferences(): RefreshTokenPreferences? {
        return if (::refreshTokenPreferences.isInitialized) {
            refreshTokenPreferences
        } else {
            refreshTokenPreferences
        }
    }

    override suspend fun writeRefreshTokenPreferences(refreshTokenPreferences: RefreshTokenPreferences) {
        this.refreshTokenPreferences = refreshTokenPreferences
    }
}