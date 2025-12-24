package ru.wolfram.registration_for_email_code.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.wolfram.common.di.DispatchersIOQualifier
import ru.wolfram.registration_for_email_code.di.RegistrationForEmailCodeScope
import ru.wolfram.registration_for_email_code.domain.model.RegistrationForEmailCodeContainer
import ru.wolfram.registration_for_email_code.domain.model.RegistrationForEmailCodeState
import ru.wolfram.registration_for_email_code.domain.usecase.RegisterForEmailCodeUseCase
import javax.inject.Inject

@RegistrationForEmailCodeScope
class RegistrationForEmailCodeViewModel @Inject internal constructor(
    private val registerForEmailCodeUseCase: RegisterForEmailCodeUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableSharedFlow<RegistrationForEmailCodeState>()
    internal val state = _state.asSharedFlow()

    fun registerForEmailCode(username: String, password: String, email: String) {
        viewModelScope.launch(ioDispatcher) {
            val result = registerForEmailCodeUseCase(
                RegistrationForEmailCodeContainer(
                    username,
                    password,
                    email
                )
            )

            Log.e(tag, "result: $result")

            if (result.isSuccess) {
                _state.emit(RegistrationForEmailCodeState.Success)
                Log.e(tag, "success!")
            } else {
                Log.e(tag, "failure!")
                _state.emit(RegistrationForEmailCodeState.Failure)
            }
        }
    }

    companion object {
        private val tag = "${RegistrationForEmailCodeViewModel::class.simpleName}"
    }
}