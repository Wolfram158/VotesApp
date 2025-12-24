package ru.wolfram.registration_with_email_code.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.wolfram.registration_with_email_code.di.RegistrationWithEmailCodeScope
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
@RegistrationWithEmailCodeScope
class RegistrationWithEmailCodeViewModelFactory @Inject constructor(
    private val provider: Provider<RegistrationWithEmailCodeViewModel>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.simpleName == RegistrationWithEmailCodeViewModel::class.simpleName) {
            provider.get() as T
        } else {
            throw RuntimeException("RegistrationWithEmailCodeViewModel is expected, but given: $modelClass")
        }
    }
}