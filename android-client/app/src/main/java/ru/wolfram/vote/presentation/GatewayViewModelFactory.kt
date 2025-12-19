package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
class GatewayViewModelFactory(
    private val provider: Provider<GatewayViewModel>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.simpleName == GatewayViewModel::class.simpleName) {
            provider.get() as T
        } else {
            throw RuntimeException("GatewayViewModel is expected, but given: $modelClass")
        }
    }
}