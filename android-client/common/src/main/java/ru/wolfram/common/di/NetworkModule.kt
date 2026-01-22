package ru.wolfram.common.di

import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
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
import ru.wolfram.common.data.network.NetworkConstants
import ru.wolfram.common.data.network.dto.Tokens
import ru.wolfram.common.data.network.service.ApiService
import ru.wolfram.common.data.security.AccessTokenPreferences
import ru.wolfram.common.data.security.RefreshTokenPreferences

@Module
object NetworkModule {
    @AppScope
    @Provides
    fun provideJson(): Json {
        return Json {
        }
    }

    @AppScope
    @Provides
    // @ApiServiceQualifier
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

    fun String.bearer() = "$BEARER $this"

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
            .addInterceptor { chain ->
                val request = chain.request()
                val header = request.header(NetworkConstants.AUTHORIZATION_HEADER)
                val newRequest = if (
                    header != null && !header.contains(BEARER)
                ) {
                    request.newBuilder()
                        .header(NetworkConstants.AUTHORIZATION_HEADER, header.bearer())
                        .build()
                } else {
                    request.newBuilder().build()
                }
                return@addInterceptor chain.proceed(newRequest)
            }
            .authenticator(object : Authenticator {
                override fun authenticate(
                    route: Route?,
                    response: Response
                ): Request? {
                    return runBlocking(ioDispatcher) {
                        val newToken = refreshTokens()
                        if (newToken != null) {
                            response
                                .request
                                .newBuilder()
                                .header(NetworkConstants.AUTHORIZATION_HEADER, newToken.bearer())
                                .build()
                        } else {
                            null
                        }
                    }
                }

                private suspend fun refreshTokens(): String? {
                    val refreshToken = refreshTokenStore.data.firstOrNull()?.token
                        ?: return null

                    return try {
                        val request = Request.Builder()
                            .url(REFRESH_TOKEN_URL)
                            .header(NetworkConstants.AUTHORIZATION_HEADER, refreshToken.bearer())
                            .build()

                        val response = client.newCall(request).execute()

                        if (response.isSuccessful) {
                            val tokens = json.decodeFromString<Tokens>(response.body.string())

                            accessTokenStore.updateData { AccessTokenPreferences(tokens.token) }
                            refreshTokenStore.updateData { RefreshTokenPreferences(tokens.refreshToken) }

                            tokens.token
                        } else {
                            null
                        }
                    } catch (_: Exception) {
                        null
                    }
                }
            })
            .build()
    }

    const val BASE_URL = "http://10.0.2.2:8080/api/v1/"
    const val REFRESH_TOKEN_URL = BASE_URL + NetworkConstants.REFRESH_TOKEN_ENDPOINT
    const val REFRESH_TOKEN = "REFRESH_TOKEN"
    const val BEARER = "Bearer"
}