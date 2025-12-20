package ru.wolfram.vote.di

import dagger.Component
import ru.wolfram.vote.presentation.RegistrationWithEmailCodeViewModelFactory

@RegistrationWithEmailCodeScope
@Component(
    dependencies = [AppComponent::class],
    modules = [RegistrationWithEmailCodeModule::class]
)
interface RegistrationWithEmailCodeComponent {
    @RegistrationWithEmailCodeScope
    fun getRegistrationWithEmailCodeViewModelFactory(): RegistrationWithEmailCodeViewModelFactory
}