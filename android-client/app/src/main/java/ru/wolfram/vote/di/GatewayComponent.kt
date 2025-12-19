package ru.wolfram.vote.di

import dagger.Component

@GatewayScope
@Component(
    dependencies = [AppComponent::class],
    modules = [GatewayModule::class]
)
interface GatewayComponent {
}