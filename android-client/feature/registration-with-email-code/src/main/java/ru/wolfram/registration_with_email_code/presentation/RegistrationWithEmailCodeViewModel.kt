package ru.wolfram.registration_with_email_code.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.wolfram.common.di.DispatchersIOQualifier
import ru.wolfram.registration_with_email_code.di.RegistrationWithEmailCodeScope
import ru.wolfram.registration_with_email_code.domain.model.RegistrationWithEmailCodeContainer
import ru.wolfram.registration_with_email_code.domain.model.RegistrationWithEmailCodeState
import ru.wolfram.registration_with_email_code.domain.usecase.RegisterWithEmailCodeUseCase
import javax.inject.Inject

@RegistrationWithEmailCodeScope
class RegistrationWithEmailCodeViewModel @Inject internal constructor(
    private val registerWithEmailCodeUseCase: RegisterWithEmailCodeUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableSharedFlow<RegistrationWithEmailCodeState>()
    internal val state = _state.asSharedFlow()

    fun registerWithEmailCode(username: String, email: String, password: String, code: String) {
        viewModelScope.launch(ioDispatcher) {
            val result = registerWithEmailCodeUseCase(
                RegistrationWithEmailCodeContainer(username, email, password, code)
            )

            if (result.isSuccess) {
                _state.emit(RegistrationWithEmailCodeState.Success)
            } else {
                _state.emit(RegistrationWithEmailCodeState.Failure)
            }
        }
    }
}