package ru.wolfram.vote.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.wolfram.vote.di.CreateVoteScope
import ru.wolfram.vote.di.DispatchersIOQualifier
import ru.wolfram.vote.domain.create_vote.model.CreatingStatus
import ru.wolfram.vote.domain.create_vote.usecase.CreateVoteUseCase
import ru.wolfram.vote.domain.create_vote.usecase.GetCreatingStatusFlowUseCase
import javax.inject.Inject

@CreateVoteScope
class CreateVoteViewModel @Inject constructor(
    getCreatingStatusFlowUseCase: GetCreatingStatusFlowUseCase,
    private val createVoteUseCase: CreateVoteUseCase,
    @param:DispatchersIOQualifier private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _variants = MutableStateFlow(CreateVoteVariants())
    val variants = _variants.asStateFlow()

    private val viewMode = MutableSharedFlow<CreatingStatus.Initial>()

    private val editMode = MutableSharedFlow<CreatingStatus.Edit>()

    private val newMode = MutableSharedFlow<CreatingStatus.New>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val creatingStatus =
        flowOf(
            getCreatingStatusFlowUseCase()
                .shareIn(
                    viewModelScope,
                    SharingStarted.Lazily
                ),
            viewMode,
            editMode,
            newMode
        )
            .flattenMerge()
            .shareIn(
                viewModelScope,
                SharingStarted.Lazily
            )
            .onEach {
                Log.e(tag, it.toString())
            }

    fun createVote() {
        viewModelScope.launch(ioDispatcher) {
            createVoteUseCase(
                _title.value,
                _variants.value.variants.map { it.variant.trim() }.filter { it.isNotBlank() }
                    .toSet()
            )
        }
    }

    fun enableViewMode() {
        viewModelScope.launch {
            viewMode.emit(CreatingStatus.Initial)
        }
    }

    fun enableEditMode(createVoteVariant: CreateVoteVariant) {
        viewModelScope.launch {
            editMode.emit(CreatingStatus.Edit(createVoteVariant))
        }
    }

    fun enableNewMode() {
        viewModelScope.launch {
            newMode.emit(CreatingStatus.New)
        }
    }

    fun updateTitle(newTitle: String) {
        _title.update {
            newTitle
        }
    }

    fun appendVariant(variant: String) {
        _variants.update {
            it.copy(it.variants + CreateVoteVariant(variant = variant))
        }
    }

    fun removeVariant(variant: CreateVoteVariant) {
        _variants.update {
            it.copy(it.variants - variant)
        }
    }

    fun updateVariant(oldVariant: CreateVoteVariant, newVariant: CreateVoteVariant) {
        _variants.update {
            it.copy(variants = it.variants.map { variant ->
                if (variant == oldVariant) {
                    newVariant
                } else {
                    variant
                }
            })
        }
    }

    companion object {
        private val tag = CreateVoteViewModel::class.simpleName
    }
}