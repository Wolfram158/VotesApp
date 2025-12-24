package ru.wolfram.gateway.di

import dagger.Component
import ru.wolfram.common.di.AppComponent
import ru.wolfram.gateway.presentation.GatewayViewModelFactory

@GatewayScope
@Component(
    dependencies = [AppComponent::class],
    modules = [GatewayModule::class]
)
interface GatewayComponent {
    @GatewayScope
    fun getGatewayViewModelFactory(): GatewayViewModelFactory
}