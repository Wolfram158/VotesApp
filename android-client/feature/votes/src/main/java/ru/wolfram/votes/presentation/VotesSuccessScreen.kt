package ru.wolfram.votes.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.wolfram.common.R
import ru.wolfram.common.presentation.test.NodeTags
import ru.wolfram.common.presentation.theme.AppTheme
import ru.wolfram.common.presentation.theme.LocalAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VotesSuccessScreen(
    titles: List<String>,
    onInitVotesGetting: () -> Unit,
    onNavigateToVote: (title: String) -> Unit,
    onNavigateToCreateVote: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.votes),
                        fontSize = AppTheme.textSize1
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            onInitVotesGetting()
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateToCreateVote()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.create_vote)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag(NodeTags.VOTES_ITEMS_LAZY_COLUMN),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(titles.size, { titles[it] }) { index ->
                Card(
                    onClick = {
                        onNavigateToVote(titles[index])
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(titles[index]),
                    shape = RectangleShape
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = titles[index],
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center,
                            maxLines = 10,
                            fontSize = LocalAppTheme.current.textSize1
                        )
                    }
                }
            }
        }
    }
}