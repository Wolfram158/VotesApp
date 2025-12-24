package ru.wolfram.create_vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.wolfram.create_vote.di.CreateVoteScope
import javax.inject.Inject
import javax.inject.Provider

@CreateVoteScope
@Suppress("UNCHECKED_CAST")
class CreateVoteViewModelFactory @Inject constructor(
    private val provider: Provider<CreateVoteViewModel>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.simpleName == CreateVoteViewModel::class.simpleName) {
            provider.get() as T
        } else {
            throw RuntimeException("CreateVoteViewModel is expected, but given: $modelClass")
        }
    }
}