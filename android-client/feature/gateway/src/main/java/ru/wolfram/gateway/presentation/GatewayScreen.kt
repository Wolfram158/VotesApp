package ru.wolfram.gateway.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.wolfram.gateway.domain.model.RefreshForEmailCodeState
import ru.wolfram.gateway.domain.model.TryToEnterState
import ru.wolfram.common.R
import ru.wolfram.votes_app.presentation.theme.LocalAppTheme

@Composable
fun GatewayScreen(
    viewModel: GatewayViewModel,
    onNavigateToVotes: () -> Unit,
    onNavigateToRefreshWithEmailCode: (username: String) -> Unit,
    onNavigateToRegistration: () -> Unit
) {
    val refreshState = viewModel.refreshState.collectAsState(RefreshForEmailCodeState.Initial)
    val tryState = viewModel.tryState.collectAsState(TryToEnterState.Initial)
    val username = remember { mutableStateOf("") }

    if (refreshState.value is RefreshForEmailCodeState.Success) {
        onNavigateToRefreshWithEmailCode(username.value)
    }

    if (tryState.value is TryToEnterState.Accept) {
        onNavigateToVotes()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (tryState.value is TryToEnterState.Failure
            || refreshState.value is RefreshForEmailCodeState.Failure
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.error_occurred)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
            },
            placeholder = {
                Text(
                    text = "username",
                    fontSize = LocalAppTheme.current.textSize1
                )
            },
            textStyle = TextStyle.Default.copy(fontSize = LocalAppTheme.current.textSize1)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.tryToEnter(username.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RectangleShape
        ) {
            Text(
                text = stringResource(R.string.enter),
                fontSize = LocalAppTheme.current.textSize1,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                onNavigateToRegistration()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RectangleShape
        ) {
            Text(
                text = stringResource(R.string.register),
                fontSize = LocalAppTheme.current.textSize1,
                textAlign = TextAlign.Center
            )
        }
    }
}