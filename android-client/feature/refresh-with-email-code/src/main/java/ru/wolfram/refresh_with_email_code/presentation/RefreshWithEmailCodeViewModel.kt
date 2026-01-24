package ru.wolfram.refresh_with_email_code.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.wolfram.common.di.DispatchersIOQualifier
import ru.wolfram.refresh_with_email_code.di.RefreshWithEmailCodeScope
import ru.wolfram.refresh_with_email_code.domain.model.RefreshWithEmailCodeContainer
import ru.wolfram.refresh_with_email_code.domain.model.RefreshWithEmailCodeState
import ru.wolfram.refresh_with_email_code.domain.usecase.RefreshWithEmailCodeUseCase
import javax.inject.Inject

@RefreshWithEmailCodeScope
internal class RefreshWithEmailCodeViewModel @Inject constructor(
    private val refreshWithEmailCodeUseCase: RefreshWithEmailCodeUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableSharedFlow<RefreshWithEmailCodeState>()
    internal val state = _state.asSharedFlow()

    fun refreshWithEmailCode(username: String, code: String) {
        viewModelScope.launch(ioDispatcher) {
            val result = refreshWithEmailCodeUseCase(
                RefreshWithEmailCodeContainer(username, code)
            )

            if (result.isSuccess) {
                _state.emit(RefreshWithEmailCodeState.Success)
            } else {
                _state.emit(RefreshWithEmailCodeState.Failure)
            }
        }
    }
}