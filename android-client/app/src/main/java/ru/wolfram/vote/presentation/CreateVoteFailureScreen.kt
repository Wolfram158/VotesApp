package ru.wolfram.vote.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.wolfram.vote.R
import ru.wolfram.vote.presentation.theme.LocalAppTheme

@Composable
fun CreateVoteFailureScreen(
    onTryAgain: () -> Unit,
    onBackToEdit: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.could_not_create_vote),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = LocalAppTheme.current.textSize1
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            {
                onTryAgain()
            }
        ) {
            Text(
                text = stringResource(R.string.try_to_create_vote_again),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = LocalAppTheme.current.textSize1
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            {
                onBackToEdit()
            }
        ) {
            Text(
                text = stringResource(R.string.back_to_vote_creating),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = LocalAppTheme.current.textSize1
            )
        }
    }
}