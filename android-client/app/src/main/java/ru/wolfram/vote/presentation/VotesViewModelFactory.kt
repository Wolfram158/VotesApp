package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.wolfram.vote.di.VotesScope
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
@VotesScope
class VotesViewModelFactory @Inject constructor(
    private val provider: Provider<VotesViewModel>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.simpleName == VotesViewModel::class.simpleName) {
            provider.get() as T
        } else {
            throw RuntimeException("VotesViewModel is expected, but given: $modelClass")
        }
    }
}