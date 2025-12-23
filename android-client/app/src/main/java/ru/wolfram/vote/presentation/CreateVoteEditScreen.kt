package ru.wolfram.vote.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import ru.wolfram.vote.R
import ru.wolfram.vote.presentation.theme.LocalAppTheme

@Composable
fun CreateVoteEditScreen(
    createVoteViewModel: CreateVoteViewModel,
    oldVariant: CreateVoteVariant
) {
    val current = remember { mutableStateOf(oldVariant.copy()) }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            TextField(
                value = current.value.variant,
                onValueChange = {
                    current.value = current.value.copy(variant = it)
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.text),
                        fontSize = LocalAppTheme.current.textSize1
                    )
                },
                textStyle = TextStyle.Default.copy(fontSize = LocalAppTheme.current.textSize1)
            )
        }
        item {
            Button(
                onClick = {
                    createVoteViewModel.updateVariant(oldVariant, current.value)
                    createVoteViewModel.enableViewMode()
                }
            ) {
                Text(
                    text = stringResource(R.string.save),
                    fontSize = LocalAppTheme.current.textSize1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}