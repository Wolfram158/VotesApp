package ru.wolfram.vote.di

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
}