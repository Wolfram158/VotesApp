package ru.wolfram.vote.di

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.data.security.AccessTokenPreferences
import ru.wolfram.vote.data.security.RefreshTokenPreferences
import ru.wolfram.vote.data.security.UsernamePreferences

@AppScope
@Component(
    modules = [
        DispatchersModule::class,
        SecurityModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    val apiService: ApiService

    val accessTokenPreferencesStore: DataStore<AccessTokenPreferences>

    val usernamePreferencesStore: DataStore<UsernamePreferences>

    val refreshTokenPreferences: DataStore<RefreshTokenPreferences>

    @DispatchersIOQualifier
    fun getIODispatcher(): CoroutineDispatcher

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}