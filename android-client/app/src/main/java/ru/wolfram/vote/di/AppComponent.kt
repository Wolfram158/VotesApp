package ru.wolfram.vote.di

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.BindsInstance
import dagger.Component
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.data.security.AccessTokenPreferences

@AppScope
@Component(
    modules = [
        DispatchersModule::class,
        SecurityModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    @AppScope
    val apiService: ApiService

    @AppScope
    val accessTokenPreferencesStore: DataStore<AccessTokenPreferences>

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}