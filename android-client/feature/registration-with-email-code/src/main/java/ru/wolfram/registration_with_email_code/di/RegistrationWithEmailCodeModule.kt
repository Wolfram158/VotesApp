package ru.wolfram.registration_with_email_code.di

import dagger.Binds
import dagger.Module
import ru.wolfram.registration_with_email_code.data.repository.RegistrationWithEmailCodeRepositoryImpl
import ru.wolfram.registration_with_email_code.domain.repository.RegistrationWithEmailCodeRepository

@Module
internal interface RegistrationWithEmailCodeModule {
    @RegistrationWithEmailCodeScope
    @Binds
    fun bindRegistrationWithEmailCodeRepository(
        impl: RegistrationWithEmailCodeRepositoryImpl
    ): RegistrationWithEmailCodeRepository
}