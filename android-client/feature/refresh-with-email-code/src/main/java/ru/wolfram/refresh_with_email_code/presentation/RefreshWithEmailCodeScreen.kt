package ru.wolfram.refresh_with_email_code.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.wolfram.common.R
import ru.wolfram.refresh_with_email_code.domain.model.RefreshWithEmailCodeState
import ru.wolfram.votes_app.presentation.theme.LocalAppTheme

@Composable
fun RefreshWithEmailCodeScreen(
    viewModel: RefreshWithEmailCodeViewModel,
    username: String,
    onSuccessfulRefresh: () -> Unit
) {
    val state = viewModel.state.collectAsState(RefreshWithEmailCodeState.Initial)
    val code = remember { mutableStateOf("") }

    if (state.value is RefreshWithEmailCodeState.Success) {
        onSuccessfulRefresh()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.value is RefreshWithEmailCodeState.Failure) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.error_occurred)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        TextField(
            value = code.value,
            onValueChange = {
                code.value = it
            },
            textStyle = TextStyle.Default.copy(
                fontSize = LocalAppTheme.current.textSize1,
                textAlign = TextAlign.Center
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.code),
                    fontSize = LocalAppTheme.current.textSize1
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.refreshWithEmailCode(
                    code = code.value,
                    username = username
                )
            }
        ) {
            Text(
                text = stringResource(R.string.confirm),
                fontSize = LocalAppTheme.current.textSize1,
                textAlign = TextAlign.Center
            )
        }
    }
}