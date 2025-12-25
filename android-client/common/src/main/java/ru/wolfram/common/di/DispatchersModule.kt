package ru.wolfram.common.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object DispatchersModule {
    @AppScope
    @Provides
    @DispatchersIOQualifier
    fun provideDispatchersIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @AppScope
    @Provides
    @DispatchersMainQualifier
    fun provideDispatchersMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}