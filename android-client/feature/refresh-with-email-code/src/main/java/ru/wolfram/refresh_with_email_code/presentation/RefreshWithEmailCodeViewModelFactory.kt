package ru.wolfram.refresh_with_email_code.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.wolfram.refresh_with_email_code.di.RefreshWithEmailCodeScope
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
@RefreshWithEmailCodeScope
internal class RefreshWithEmailCodeViewModelFactory @Inject constructor(
    private val provider: Provider<RefreshWithEmailCodeViewModel>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.simpleName == RefreshWithEmailCodeViewModel::class.simpleName) {
            provider.get() as T
        } else {
            throw RuntimeException("RefreshWithEmailCodeViewModel is expected, but given: $modelClass")
        }
    }
}