package ru.wolfram.vote.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import ru.wolfram.vote.presentation.theme.LocalAppTheme

@Composable
fun VotesSuccessScreen(
    titles: List<String>,
    onNavigateToVote: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(titles.size, { titles[it] }) {
            Card(
                onClick = {
                    onNavigateToVote()
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = titles[it],
                        textAlign = TextAlign.Center,
                        maxLines = 10,
                        fontSize = LocalAppTheme.current.textSize1
                    )
                }
            }
        }
    }
}