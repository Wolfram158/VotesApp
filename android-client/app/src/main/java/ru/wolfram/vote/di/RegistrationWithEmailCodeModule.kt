package ru.wolfram.vote.di

import dagger.Binds
import dagger.Module
import ru.wolfram.vote.data.registration_with_email_code.repository.RegistrationWithEmailCodeRepositoryImpl
import ru.wolfram.vote.domain.registration_with_email_code.repository.RegistrationWithEmailCodeRepository

@Module
interface RegistrationWithEmailCodeModule {
    @RegistrationWithEmailCodeScope
    @Binds
    fun bindRegistrationWithEmailCodeRepository(
        impl: RegistrationWithEmailCodeRepositoryImpl
    ): RegistrationWithEmailCodeRepository
}