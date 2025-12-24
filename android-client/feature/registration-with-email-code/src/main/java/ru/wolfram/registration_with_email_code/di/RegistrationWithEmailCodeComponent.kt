package ru.wolfram.registration_with_email_code.di

import dagger.Component
import ru.wolfram.common.di.AppComponent
import ru.wolfram.registration_with_email_code.presentation.RegistrationWithEmailCodeViewModelFactory

@RegistrationWithEmailCodeScope
@Component(
    dependencies = [AppComponent::class],
    modules = [RegistrationWithEmailCodeModule::class]
)
interface RegistrationWithEmailCodeComponent {
    @RegistrationWithEmailCodeScope
    fun getRegistrationWithEmailCodeViewModelFactory(): RegistrationWithEmailCodeViewModelFactory
}