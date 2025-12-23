package ru.wolfram.vote.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.wolfram.vote.domain.create_vote.model.CreatingStatus

@Composable
fun CreateVoteScreen(
    createVoteViewModel: CreateVoteViewModel,
    onVoteCreated: () -> Unit
) {
    val creatingStatus = createVoteViewModel.creatingStatus.collectAsState(CreatingStatus.Initial)

    when (val status = creatingStatus.value) {
        CreatingStatus.Error -> {
            CreateVoteFailureScreen(
                createVoteViewModel::createVote,
                createVoteViewModel::enableViewMode
            )
        }

        CreatingStatus.Initial -> {
            CreateVoteInitialScreen(createVoteViewModel)
        }

        CreatingStatus.Success -> {
            onVoteCreated()
        }

        is CreatingStatus.Edit -> {
            CreateVoteEditScreen(
                createVoteViewModel,
                status.variant
            )
        }

        CreatingStatus.New -> {
            CreateVoteNewScreen(
                createVoteViewModel
            )
        }
    }
}