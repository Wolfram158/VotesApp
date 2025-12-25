package ru.wolfram.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import ru.wolfram.common.data.network.service.ApiService
import ru.wolfram.common.data.security.AccessTokenPreferences
import ru.wolfram.common.data.security.RefreshTokenPreferences
import ru.wolfram.common.data.security.UsernamePreferences

@AppScope
@Component(
    modules = [
        DispatchersModule::class,
        SecurityModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    val accessTokenPreferencesStore: DataStore<AccessTokenPreferences>

    val usernamePreferencesStore: DataStore<UsernamePreferences>

    val refreshTokenPreferences: DataStore<RefreshTokenPreferences>

    fun getApiService(): ApiService

    @DispatchersIOQualifier
    fun getIODispatcher(): CoroutineDispatcher

    @DispatchersMainQualifier
    fun getMainDispatcher(): CoroutineDispatcher

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance /* @ApiServiceTestQualifier */ apiService: ApiService
        ): AppComponent
    }
}