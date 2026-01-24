package ru.wolfram.create_vote.di

import dagger.Component
import ru.wolfram.common.di.AppComponent
import ru.wolfram.create_vote.presentation.CreateVoteViewModelFactory

@CreateVoteScope
@Component(
    dependencies = [AppComponent::class],
    modules = [CreateVoteModule::class]
)
internal interface CreateVoteComponent {
    @CreateVoteScope
    fun getCreateVoteViewModelFactory(): CreateVoteViewModelFactory
}