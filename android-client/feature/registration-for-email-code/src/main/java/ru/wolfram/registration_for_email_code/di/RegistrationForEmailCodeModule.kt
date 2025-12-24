package ru.wolfram.registration_for_email_code.di

import dagger.Binds
import dagger.Module
import ru.wolfram.registration_for_email_code.data.repository.RegistrationForEmailCodeRepositoryImpl
import ru.wolfram.registration_for_email_code.domain.repository.RegistrationForEmailCodeRepository

@Module
internal interface RegistrationForEmailCodeModule {
    @RegistrationForEmailCodeScope
    @Binds
    fun bindRegistrationForEmailCodeRepository(impl: RegistrationForEmailCodeRepositoryImpl): RegistrationForEmailCodeRepository
}