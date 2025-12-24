package ru.wolfram.create_vote.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.wolfram.create_vote.domain.model.CreatingStatus

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