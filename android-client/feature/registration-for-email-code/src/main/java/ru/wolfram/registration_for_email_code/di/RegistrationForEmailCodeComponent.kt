package ru.wolfram.registration_for_email_code.di

import dagger.Component
import ru.wolfram.common.di.AppComponent
import ru.wolfram.registration_for_email_code.presentation.RegistrationForEmailCodeViewModelFactory

@RegistrationForEmailCodeScope
@Component(
    dependencies = [AppComponent::class],
    modules = [RegistrationForEmailCodeModule::class]
)
internal interface RegistrationForEmailCodeComponent {
    @RegistrationForEmailCodeScope
    fun getRegistrationForEmailCodeViewModelFactory(): RegistrationForEmailCodeViewModelFactory
}