package ru.wolfram.votes_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
class VoteViewModelFactory(
    private val provider: Provider<VoteViewModel>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.simpleName == VoteViewModel::class.simpleName) {
            provider.get() as T
        } else {
            throw RuntimeException("VoteViewModel is expected, but given: $modelClass")
        }
    }
}