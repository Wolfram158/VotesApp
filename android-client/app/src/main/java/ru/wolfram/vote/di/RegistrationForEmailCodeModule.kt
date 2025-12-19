package ru.wolfram.vote.di

import dagger.Binds
import dagger.Module
import ru.wolfram.vote.data.registration_for_email_code.repository.RegistrationForEmailCodeRepositoryImpl
import ru.wolfram.vote.domain.registration_for_email_code.repository.RegistrationForEmailCodeRepository

@Module
interface RegistrationForEmailCodeModule {
    @RegistrationForEmailCodeScope
    @Binds
    fun bindRegistrationForEmailCodeRepository(impl: RegistrationForEmailCodeRepositoryImpl): RegistrationForEmailCodeRepository
}