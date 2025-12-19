package ru.wolfram.vote.di

import dagger.Component
import ru.wolfram.vote.presentation.CreateVoteViewModelFactory

@CreateVoteScope
@Component(
    dependencies = [AppComponent::class],
    modules = [CreateVoteModule::class]
)
interface CreateVoteComponent {
    @CreateVoteScope
    fun getCreateVoteViewModelFactory(): CreateVoteViewModelFactory
}