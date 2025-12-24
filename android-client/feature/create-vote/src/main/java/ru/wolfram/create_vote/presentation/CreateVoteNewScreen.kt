package ru.wolfram.create_vote.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.wolfram.vote.R
import ru.wolfram.vote.presentation.theme.LocalAppTheme

@Composable
internal fun CreateVoteNewScreen(
    createVoteViewModel: CreateVoteViewModel
) {
    val current = remember { mutableStateOf(CreateVoteVariant("")) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        item {
            TextField(
                value = current.value.variant,
                onValueChange = {
                    current.value = current.value.copy(variant = it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
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
                    createVoteViewModel.appendVariant(current.value.variant)
                    createVoteViewModel.enableViewMode()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                shape = RectangleShape
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