package ru.wolfram.vote.di

import dagger.Component

@CreateVoteScope
@Component(
    dependencies = [AppComponent::class],
    modules = [CreateVoteModule::class]
)
interface CreateVoteComponent {
}