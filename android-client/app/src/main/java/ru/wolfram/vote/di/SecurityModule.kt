package ru.wolfram.vote.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import ru.wolfram.vote.data.security.AccessTokenPreferences
import ru.wolfram.vote.data.security.AndroidCrypto
import ru.wolfram.vote.data.security.EmailPreferences
import ru.wolfram.vote.data.security.RefreshTokenPreferences
import ru.wolfram.vote.data.security.SerializerProvider
import ru.wolfram.vote.data.security.UsernamePreferences
import ru.wolfram.vote.domain.security.Crypto

@Module
abstract class SecurityModule {
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

    @Binds
    abstract fun bindCrypto(impl: AndroidCrypto): Crypto
}