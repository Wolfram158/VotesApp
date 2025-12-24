package ru.wolfram.refresh_with_email_code.di

import dagger.Binds
import dagger.Module
import ru.wolfram.refresh_with_email_code.data.repository.RefreshWithEmailCodeRepositoryImpl
import ru.wolfram.refresh_with_email_code.domain.repository.RefreshWithEmailCodeRepository

@Module
internal interface RefreshWithEmailCodeModule {
    @RefreshWithEmailCodeScope
    @Binds
    fun bindRefreshWithEmailCodeRepository(impl: RefreshWithEmailCodeRepositoryImpl): RefreshWithEmailCodeRepository
}