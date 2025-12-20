package ru.wolfram.vote.di

import dagger.Component
import ru.wolfram.vote.presentation.RegistrationForEmailCodeViewModelFactory

@RegistrationForEmailCodeScope
@Component(
    dependencies = [AppComponent::class],
    modules = [RegistrationForEmailCodeModule::class]
)
interface RegistrationForEmailCodeComponent {
    @RegistrationForEmailCodeScope
    fun getRegistrationForEmailCodeViewModelFactory(): RegistrationForEmailCodeViewModelFactory
}