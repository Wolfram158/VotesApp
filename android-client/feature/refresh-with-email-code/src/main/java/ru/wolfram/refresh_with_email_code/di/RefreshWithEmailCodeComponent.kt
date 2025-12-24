package ru.wolfram.refresh_with_email_code.di

import dagger.Component
import ru.wolfram.common.di.AppComponent
import ru.wolfram.refresh_with_email_code.presentation.RefreshWithEmailCodeViewModelFactory

@RefreshWithEmailCodeScope
@Component(
    dependencies = [AppComponent::class],
    modules = [RefreshWithEmailCodeModule::class]
)
interface RefreshWithEmailCodeComponent {
    @RefreshWithEmailCodeScope
    fun getRefreshWithEmailCodeViewModelFactory(): RefreshWithEmailCodeViewModelFactory
}