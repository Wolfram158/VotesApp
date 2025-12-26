package ru.wolfram.common.di

import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import ru.wolfram.common.data.security.AccessTokenPreferences
import ru.wolfram.common.data.security.EmailPreferences
import ru.wolfram.common.data.security.RefreshTokenPreferences
import ru.wolfram.common.data.security.UsernamePreferences
import ru.wolfram.common.data.storage.LocalDataStorageImpl
import ru.wolfram.common.domain.storage.LocalDataStorage

@Module
internal object LocalDataStorageModule {
    @AppScope
    @LocalDataStorageQualifier
    @Provides
    fun provideLocalDataStorage(
        accessTokenStore: DataStore<AccessTokenPreferences>,
        refreshTokenStore: DataStore<RefreshTokenPreferences>,
        usernameStore: DataStore<UsernamePreferences>,
        emailStore: DataStore<EmailPreferences>
    ): LocalDataStorage {
        return LocalDataStorageImpl(
            accessTokenStore = accessTokenStore,
            refreshTokenStore = refreshTokenStore,
            usernameStore = usernameStore,
            emailStore = emailStore
        )
    }
}