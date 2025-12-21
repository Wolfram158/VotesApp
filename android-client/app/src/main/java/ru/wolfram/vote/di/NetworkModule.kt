package ru.wolfram.vote.di

import android.util.Log
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import ru.wolfram.vote.data.network.dto.Tokens
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.data.security.AccessTokenPreferences
import ru.wolfram.vote.data.security.RefreshTokenPreferences

@Module
class NetworkModule {
    @AppScope
    @Provides
    fun provideJson(): Json {
        return Json {
        }
    }

    @AppScope
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient, json: Json): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=utf-8".toMediaType())
            )
            .build()
            .create<ApiService>()
    }

    @AppScope
    @Provides
    fun provideOkHttpClient(
        accessTokenStore: DataStore<AccessTokenPreferences>,
        refreshTokenStore: DataStore<RefreshTokenPreferences>,
        json: Json,
        @DispatchersIOQualifier ioDispatcher: CoroutineDispatcher
    ): OkHttpClient {
        val mutex = Mutex()
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val response = chain.proceed(chain.request())

                if (response.code == 401 && !response.request.url.queryParameterNames.contains(
                        QUERY_REFRESH_TOKEN
                    )
                ) {
                    Log.i(REFRESH_TOKEN, "refresh token is needed")
                    runBlocking(ioDispatcher) {
                        mutex.withLock {
                            val refreshToken = refreshTokenStore.data.firstOrNull()?.token
                            refreshToken?.let {
                                val newClient = OkHttpClient.Builder().build()
                                val response = newClient.newCall(
                                    Request
                                        .Builder()
                                        .url("$REFRESH_TOKEN_URL?$QUERY_REFRESH_TOKEN=$refreshToken")
                                        .build()
                                ).execute()
                                if (response.code == 200) {
                                    val tokens =
                                        json.decodeFromString<Tokens>(
                                            response.body.string()
                                        )
                                    Log.i(REFRESH_TOKEN, "new tokens: $tokens")
                                    accessTokenStore.updateData {
                                        AccessTokenPreferences(tokens.token)
                                    }
                                    refreshTokenStore.updateData {
                                        RefreshTokenPreferences(tokens.refreshToken)
                                    }
                                }
                            }
                        }
                    }
                }

                if (response.code == 404 && response.request.url.queryParameterNames.contains(
                        QUERY_REFRESH_TOKEN
                    )
                ) {
                    response.newBuilder().code(404).build()
                }

                response
            }
            .build()
    }

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080/api/v1/"
        const val REFRESH_TOKEN_URL = BASE_URL + "auth/refresh-token"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val QUERY_REFRESH_TOKEN = "refreshToken"
    }
}