package ru.wolfram.vote.di

import dagger.Component

@RefreshWithEmailCodeScope
@Component(
    dependencies = [AppComponent::class],
    modules = [RefreshWithEmailCodeModule::class]
)
interface RefreshWithEmailCodeComponent {
}