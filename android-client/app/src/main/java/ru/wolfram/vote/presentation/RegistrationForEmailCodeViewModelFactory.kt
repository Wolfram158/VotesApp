package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.wolfram.vote.di.RegistrationForEmailCodeScope
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
@RegistrationForEmailCodeScope
class RegistrationForEmailCodeViewModelFactory @Inject constructor(
    private val provider: Provider<RegistrationForEmailCodeViewModel>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.simpleName == RegistrationForEmailCodeViewModel::class.simpleName) {
            provider.get() as T
        } else {
            throw RuntimeException("RegistrationForEmailCodeViewModel is expected, but given: $modelClass")
        }
    }
}