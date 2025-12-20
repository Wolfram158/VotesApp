package ru.wolfram.vote.di

import dagger.Component
import ru.wolfram.vote.presentation.RefreshWithEmailCodeViewModelFactory

@RefreshWithEmailCodeScope
@Component(
    dependencies = [AppComponent::class],
    modules = [RefreshWithEmailCodeModule::class]
)
interface RefreshWithEmailCodeComponent {
    @RefreshWithEmailCodeScope
    fun getRefreshWithEmailCodeViewModelFactory(): RefreshWithEmailCodeViewModelFactory
}