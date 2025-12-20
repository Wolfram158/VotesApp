package ru.wolfram.vote.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.wolfram.vote.domain.create_vote.model.CreatingStatus

@Composable
fun CreateVoteScreen(
    createVoteViewModel: CreateVoteViewModel,
    onTryAgain: (title: String, variants: Set<String>) -> Unit,
    onVoteCreated: () -> Unit
) {
    val creatingStatus = createVoteViewModel.creatingStatus.collectAsState(CreatingStatus.Initial)

    if (creatingStatus.value is CreatingStatus.Success) {
        onVoteCreated()
    }
}