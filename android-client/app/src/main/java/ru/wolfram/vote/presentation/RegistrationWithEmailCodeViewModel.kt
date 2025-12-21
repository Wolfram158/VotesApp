package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.wolfram.vote.di.DispatchersIOQualifier
import ru.wolfram.vote.di.RegistrationWithEmailCodeScope
import ru.wolfram.vote.domain.registration_with_email_code.model.RegistrationWithEmailCodeContainer
import ru.wolfram.vote.domain.registration_with_email_code.usecase.RegisterWithEmailCodeUseCase
import javax.inject.Inject

@RegistrationWithEmailCodeScope
class RegistrationWithEmailCodeViewModel @Inject constructor(
    private val registerWithEmailCodeUseCase: RegisterWithEmailCodeUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableSharedFlow<RegistrationWithEmailCodeState>()
    val state = _state.asSharedFlow()

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