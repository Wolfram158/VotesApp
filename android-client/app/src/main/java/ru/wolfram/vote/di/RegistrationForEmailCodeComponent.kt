package ru.wolfram.vote.di

import dagger.Component

@RegistrationForEmailCodeScope
@Component(
    dependencies = [AppComponent::class],
    modules = [RegistrationForEmailCodeModule::class]
)
interface RegistrationForEmailCodeComponent {
}