package ru.wolfram.vote.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.wolfram.vote.data.gateway.repository.RefreshForEmailCodeRepositoryImpl
import ru.wolfram.vote.domain.gateway.repository.RefreshForEmailCodeRepository
import ru.wolfram.vote.presentation.GatewayViewModel
import ru.wolfram.vote.presentation.GatewayViewModelFactory
import javax.inject.Provider

@Module
interface GatewayModule {
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