package ru.wolfram.common.di

import android.util.Log
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
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
        val client = OkHttpClient
            .Builder()
            .build()
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .authenticator(object : Authenticator {
                override fun authenticate(
                    route: Route?,
                    response: Response
                ): Request? {
                    Log.e("OkHttp", "Authenticator mentioned!")
                    return runBlocking(ioDispatcher) {
                        val newToken = refreshTokens()
                        if (newToken != null) {
                            response
                                .request
                                .newBuilder()
                                .header("Authorization", newToken)
                                .build()
                        } else {
                            null
                        }
                    }
                }

                private suspend fun refreshTokens(): String? {
                    val refreshToken = refreshTokenStore.data.firstOrNull()?.token
                        ?: return run {
                            Log.e("OkHttp", "Refresh token is null!")
                            null
                        }
                    Log.e(REFRESH_TOKEN, "refresh token: $refreshToken")

                    return try {
                        val request = Request.Builder()
                            .url(REFRESH_TOKEN_URL)
                            .post(
                                FormBody
                                    .Builder()
                                    .add(QUERY_REFRESH_TOKEN, refreshToken)
                                    .build()
                            )
                            .build()

                        val response = client.newCall(request).execute()

                        if (response.isSuccessful) {
                            val tokens = json.decodeFromString<Tokens>(response.body.string())

                            accessTokenStore.updateData { AccessTokenPreferences(tokens.token) }
                            refreshTokenStore.updateData { RefreshTokenPreferences(tokens.refreshToken) }

                            Log.i(REFRESH_TOKEN, "${tokens.token} ${tokens.refreshToken}")

                            tokens.token
                        } else {
                            Log.e("OkHttp", "response failure: ${response.request.url}")
                            null
                        }
                    } catch (_: Exception) {
                        null
                    }
                }

            })
            .build()
    }

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080/api/v1/"
        const val REFRESH_TOKEN_URL = BASE_URL + "auth/refresh-token"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val QUERY_REFRESH_TOKEN = "refreshToken"
    }
}