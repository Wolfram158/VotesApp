package ru.wolfram.vote.di

import dagger.Component

@RegistrationWithEmailCodeScope
@Component(
    dependencies = [AppComponent::class],
    modules = [RegistrationWithEmailCodeModule::class]
)
interface RegistrationWithEmailCodeComponent {
}