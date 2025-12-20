package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.wolfram.vote.di.DispatchersIOQualifier
import ru.wolfram.vote.di.RegistrationForEmailCodeScope
import ru.wolfram.vote.domain.registration_for_email_code.model.RegistrationForEmailCodeContainer
import ru.wolfram.vote.domain.registration_for_email_code.usecase.RegisterForEmailCodeUseCase
import javax.inject.Inject

@RegistrationForEmailCodeScope
class RegistrationForEmailCodeViewModel @Inject constructor(
    private val registerForEmailCodeUseCase: RegisterForEmailCodeUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableSharedFlow<RegistrationForEmailCodeState>()
    val state = _state.asSharedFlow()

    fun registerForEmailCode(username: String, password: String, email: String) {
        viewModelScope.launch(ioDispatcher) {
            val result = registerForEmailCodeUseCase(
                RegistrationForEmailCodeContainer(
                    username,
                    password,
                    email
                )
            )

            if (result.isSuccess) {
                _state.emit(RegistrationForEmailCodeState.Success)
            } else {
                _state.emit(RegistrationForEmailCodeState.Failure)
            }
        }
    }
}