package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.wolfram.vote.di.DispatchersIOQualifier
import ru.wolfram.vote.di.RefreshWithEmailCodeScope
import ru.wolfram.vote.domain.refresh_with_email_code.model.RefreshWithEmailCodeContainer
import ru.wolfram.vote.domain.refresh_with_email_code.usecase.RefreshWithEmailCodeUseCase
import javax.inject.Inject

@RefreshWithEmailCodeScope
class RefreshWithEmailCodeViewModel @Inject constructor(
    private val refreshWithEmailCodeUseCase: RefreshWithEmailCodeUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableSharedFlow<RefreshWithEmailCodeState>()
    val state = _state.asSharedFlow()

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