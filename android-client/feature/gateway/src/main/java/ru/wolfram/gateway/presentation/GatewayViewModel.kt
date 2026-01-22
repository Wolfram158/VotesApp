package ru.wolfram.gateway.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.wolfram.common.di.DispatchersIOQualifier
import ru.wolfram.gateway.di.GatewayScope
import ru.wolfram.gateway.domain.model.RefreshForEmailCodeContainer
import ru.wolfram.gateway.domain.model.RefreshForEmailCodeState
import ru.wolfram.gateway.domain.model.TryToEnterState
import ru.wolfram.gateway.domain.usecase.CheckIfNeedEmailCodeUseCase
import ru.wolfram.gateway.domain.usecase.RefreshForEmailCodeUseCase
import javax.inject.Inject

@GatewayScope
class GatewayViewModel @Inject internal constructor(
    private val refreshForEmailCodeUseCase: RefreshForEmailCodeUseCase,
    private val checkIfNeedEmailCodeUseCase: CheckIfNeedEmailCodeUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _refreshState = MutableSharedFlow<RefreshForEmailCodeState>()
    val refreshState = _refreshState.asSharedFlow()

    private val _tryState = MutableSharedFlow<TryToEnterState>()
    val tryState = _tryState.asSharedFlow()

    fun refreshForEmailCode(username: String, password: String) {
        viewModelScope.launch(ioDispatcher) {
            val result = refreshForEmailCodeUseCase(
                RefreshForEmailCodeContainer(username, password)
            )

            if (result.isSuccess) {
                _refreshState.emit(RefreshForEmailCodeState.Success)
            } else {
                _refreshState.emit(RefreshForEmailCodeState.Failure)
            }
        }
    }

    fun tryToEnter(username: String, password: String) {
        viewModelScope.launch(ioDispatcher) {
            val result = checkIfNeedEmailCodeUseCase(username)

            if (result.isSuccess) {
                val need = result.getOrThrow()
                if (need) {
                    _tryState.emit(TryToEnterState.Reject)
                    refreshForEmailCode(username, password)
                } else {
                    _tryState.emit(TryToEnterState.Accept)
                }
            } else {
                _tryState.emit(TryToEnterState.Failure)
                refreshForEmailCode(username, password)
            }
        }
    }

    companion object {
        private val tag = GatewayViewModel::class.simpleName
    }
}