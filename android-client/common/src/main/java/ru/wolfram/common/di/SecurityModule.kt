package ru.wolfram.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import ru.wolfram.common.data.security.AccessTokenPreferences
import ru.wolfram.common.data.security.AndroidCrypto
import ru.wolfram.common.data.security.EmailPreferences
import ru.wolfram.common.data.security.RefreshTokenPreferences
import ru.wolfram.common.data.security.SerializerProvider
import ru.wolfram.common.data.security.UsernamePreferences
import ru.wolfram.common.domain.security.Crypto

@Module
internal interface SecurityModule {
    @AppScope
    @Binds
    fun bindCrypto(impl: AndroidCrypto): Crypto

    companion object {
        @AppScope
        @Provides
        fun provideAndroidCrypto(): AndroidCrypto = AndroidCrypto

        @AppScope
        @Provides
        fun provideAccessTokenPreferences(
            crypto: Crypto,
            json: Json,
            @DispatchersIOQualifier ioDispatcher: CoroutineDispatcher,
            context: Context
        ): DataStore<AccessTokenPreferences> {
            val serializer = SerializerProvider.provideSerializer(
                crypto = crypto,
                json = json,
                ioDispatcher = ioDispatcher,
                default = AccessTokenPreferences()
            )
            return DataStoreFactory.create(
                serializer = serializer,
                produceFile = { context.preferencesDataStoreFile("access-token-preferences") }
            )
        }

        @AppScope
        @Provides
        fun provideRefreshTokenPreferences(
            crypto: Crypto,
            json: Json,
            @DispatchersIOQualifier ioDispatcher: CoroutineDispatcher,
            context: Context
        ): DataStore<RefreshTokenPreferences> {
            val serializer = SerializerProvider.provideSerializer(
                crypto = crypto,
                json = json,
                ioDispatcher = ioDispatcher,
                default = RefreshTokenPreferences()
            )
            return DataStoreFactory.create(
                serializer = serializer,
                produceFile = { context.preferencesDataStoreFile("refresh-token-preferences") }
            )
        }

        @AppScope
        @Provides
        fun provideUsernamePreferences(
            crypto: Crypto,
            json: Json,
            @DispatchersIOQualifier ioDispatcher: CoroutineDispatcher,
            context: Context
        ): DataStore<UsernamePreferences> {
            val serializer = SerializerProvider.provideSerializer(
                crypto = crypto,
                json = json,
                ioDispatcher = ioDispatcher,
                default = UsernamePreferences()
            )
            return DataStoreFactory.create(
                serializer = serializer,
                produceFile = { context.preferencesDataStoreFile("username-preferences") }
            )
        }

        @AppScope
        @Provides
        fun provideEmailPreferences(
            crypto: Crypto,
            json: Json,
            @DispatchersIOQualifier ioDispatcher: CoroutineDispatcher,
            context: Context
        ): DataStore<EmailPreferences> {
            val serializer = SerializerProvider.provideSerializer(
                crypto = crypto,
                json = json,
                ioDispatcher = ioDispatcher,
                default = EmailPreferences()
            )
            return DataStoreFactory.create(
                serializer = serializer,
                produceFile = { context.preferencesDataStoreFile("email-preferences") }
            )
        }
    }
}