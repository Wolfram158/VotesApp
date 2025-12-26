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
import ru.wolfram.common.domain.storage.LocalDataStorage

@AppScope
@Component(
    modules = [
        DispatchersModule::class,
        SecurityModule::class,
        NetworkModule::class,
        LocalDataStorageModule::class
    ]
)
interface AppComponent {
    fun getApiService(): ApiService

    @DispatchersIOQualifier
    fun getIODispatcher(): CoroutineDispatcher

    @DispatchersMainQualifier
    fun getMainDispatcher(): CoroutineDispatcher

    fun getLocalDataStorage(): LocalDataStorage

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance @ApiServiceTestQualifier apiService: ApiService,
            @BindsInstance @LocalDataStorageTestQualifier localDataStorage: LocalDataStorage
        ): AppComponent
    }
}