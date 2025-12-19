package ru.wolfram.vote.di

import dagger.Binds
import dagger.Module
import ru.wolfram.vote.data.refresh_with_email_code.repository.RefreshWithEmailCodeRepositoryImpl
import ru.wolfram.vote.domain.refresh_with_email_code.repository.RefreshWithEmailCodeRepository

@Module
interface RefreshWithEmailCodeModule {
    @RefreshWithEmailCodeScope
    @Binds
    fun bindRefreshWithEmailCodeRepository(impl: RefreshWithEmailCodeRepositoryImpl): RefreshWithEmailCodeRepository
}