package ru.wolfram.vote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.wolfram.vote.di.DispatchersIOQualifier
import ru.wolfram.vote.di.GatewayScope
import ru.wolfram.vote.domain.gateway.model.RefreshForEmailCodeContainer
import ru.wolfram.vote.domain.gateway.usecase.CheckIfNeedEmailCodeUseCase
import ru.wolfram.vote.domain.gateway.usecase.RefreshForEmailCodeUseCase
import javax.inject.Inject

@GatewayScope
class GatewayViewModel @Inject constructor(
    private val refreshForEmailCodeUseCase: RefreshForEmailCodeUseCase,
    private val checkIfNeedEmailCodeUseCase: CheckIfNeedEmailCodeUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _refreshState = MutableSharedFlow<RefreshForEmailCodeState>()
    val refreshState = _refreshState.asSharedFlow()

    private val _tryState = MutableSharedFlow<TryToEnterState>()
    val tryState = _tryState.asSharedFlow()

    fun refreshForEmailCode(username: String) {
        viewModelScope.launch(ioDispatcher) {
            val result = refreshForEmailCodeUseCase(
                RefreshForEmailCodeContainer(username)
            )

            if (result.isSuccess) {
                _refreshState.emit(RefreshForEmailCodeState.Success)
            } else {
                _refreshState.emit(RefreshForEmailCodeState.Failure)
            }
        }
    }

    fun tryToEnter(username: String) {
        viewModelScope.launch(ioDispatcher) {
            val result = checkIfNeedEmailCodeUseCase(username)

            if (result.isSuccess) {
                val need = result.getOrThrow()
                if (need) {
                    _tryState.emit(TryToEnterState.Reject)
                    refreshForEmailCode(username)
                } else {
                    _tryState.emit(TryToEnterState.Accept)
                }
            } else {
                _tryState.emit(TryToEnterState.Failure)
                refreshForEmailCode(username)
            }
        }
    }
}