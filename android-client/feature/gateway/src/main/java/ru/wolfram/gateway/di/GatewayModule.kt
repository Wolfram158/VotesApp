package ru.wolfram.gateway.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.wolfram.gateway.data.repository.RefreshForEmailCodeRepositoryImpl
import ru.wolfram.gateway.domain.repository.RefreshForEmailCodeRepository
import ru.wolfram.gateway.presentation.GatewayViewModel
import ru.wolfram.gateway.presentation.GatewayViewModelFactory
import javax.inject.Provider

@Module
internal interface GatewayModule {
    @GatewayScope
    @Binds
    fun bindRefreshForEmailCodeRepository(impl: RefreshForEmailCodeRepositoryImpl): RefreshForEmailCodeRepository

    companion object {
        @Provides
        @GatewayScope
        fun getGatewayViewModelFactory(provider: Provider<GatewayViewModel>): GatewayViewModelFactory {
            return GatewayViewModelFactory(provider)
        }
    }
}