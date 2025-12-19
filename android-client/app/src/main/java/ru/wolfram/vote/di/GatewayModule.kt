package ru.wolfram.vote.di

import dagger.Binds
import dagger.Module
import ru.wolfram.vote.data.gateway.repository.RefreshForEmailCodeRepositoryImpl
import ru.wolfram.vote.domain.gateway.repository.RefreshForEmailCodeRepository

@Module
interface GatewayModule {
    @GatewayScope
    @Binds
    fun bindRefreshForEmailCodeRepository(impl: RefreshForEmailCodeRepositoryImpl): RefreshForEmailCodeRepository
}