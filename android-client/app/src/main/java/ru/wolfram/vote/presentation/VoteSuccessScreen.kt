package ru.wolfram.vote.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.wolfram.vote.R
import ru.wolfram.vote.domain.votes.model.Vote
import ru.wolfram.vote.presentation.theme.AppTheme
import ru.wolfram.vote.presentation.theme.LocalAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoteSuccessScreen(
    vote: List<Vote>,
    onInitVoteGetting: () -> Unit,
    onDoVote: (title: String, variant: String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.vote),
                        fontSize = AppTheme.textSize1
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            onInitVoteGetting()
                        },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Autorenew,
                            contentDescription = stringResource(R.string.reload)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Text(
                    text = vote.firstOrNull()?.title ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    fontSize = LocalAppTheme.current.textSize1,
                    textAlign = TextAlign.Center
                )
            }
            items(vote.size, { vote[it].variant }) { index ->
                Card(
                    onClick = {
                        onDoVote(vote[index].title, vote[index].variant)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = vote[index].variant,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp),
                            fontSize = LocalAppTheme.current.textSize1,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = vote[index].votesCount.toString(),
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp),
                            fontSize = LocalAppTheme.current.textSize1,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}