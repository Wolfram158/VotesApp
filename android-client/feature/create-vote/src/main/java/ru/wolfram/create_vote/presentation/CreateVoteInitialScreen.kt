package ru.wolfram.create_vote.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ru.wolfram.vote.R
import ru.wolfram.vote.presentation.theme.AppTheme
import ru.wolfram.vote.presentation.theme.LocalAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateVoteInitialScreen(
    createVoteViewModel: CreateVoteViewModel
) {
    val title = createVoteViewModel.title.collectAsState()
    val variants = createVoteViewModel.variants.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.creating_vote),
                        fontSize = AppTheme.textSize1
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            createVoteViewModel.createVote()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = stringResource(R.string.create_vote)
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
            item(key = "-2") {
                TextField(
                    value = title.value,
                    onValueChange = {
                        createVoteViewModel.updateTitle(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.title),
                            fontSize = LocalAppTheme.current.textSize1
                        )
                    },
                    textStyle = TextStyle.Default.copy(fontSize = LocalAppTheme.current.textSize1)
                )
            }
            item(key = "-1") {
                Spacer(modifier = Modifier.height(24.dp))
            }
            items(variants.value.variants, { it.id }) { item ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            createVoteViewModel.enableEditMode(item)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mode,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(
                        text = item.variant,
                        modifier = Modifier.weight(4f),
                        fontSize = AppTheme.textSize1,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    IconButton(
                        onClick = {
                            createVoteViewModel.removeVariant(item)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            createVoteViewModel.enableNewMode()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}