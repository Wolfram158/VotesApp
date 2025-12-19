package ru.wolfram.vote.di

import dagger.Component
import ru.wolfram.vote.presentation.GatewayViewModelFactory

@GatewayScope
@Component(
    dependencies = [AppComponent::class],
    modules = [GatewayModule::class]
)
interface GatewayComponent {
    @GatewayScope
    fun getGatewayViewModelFactory(): GatewayViewModelFactory
}