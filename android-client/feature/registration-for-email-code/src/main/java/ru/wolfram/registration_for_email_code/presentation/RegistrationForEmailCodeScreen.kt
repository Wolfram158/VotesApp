package ru.wolfram.registration_for_email_code.presentation

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.wolfram.common.R
import ru.wolfram.common.presentation.test.NodeTags
import ru.wolfram.common.presentation.theme.LocalAppTheme
import ru.wolfram.registration_for_email_code.domain.model.RegistrationForEmailCodeState

@Composable
internal fun RegistrationForEmailCodeScreen(
    viewModel: RegistrationForEmailCodeViewModel,
    onSuccessfullyEmailCodeSent: (username: String, email: String, password: String) -> Unit
) {
    val state = viewModel.state.collectAsState(RegistrationForEmailCodeState.Initial)
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    if (state.value is RegistrationForEmailCodeState.Success) {
        onSuccessfullyEmailCodeSent(username.value, email.value, password.value)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.value is RegistrationForEmailCodeState.Failure) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.error_occurred)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
            },
            modifier = Modifier.testTag(NodeTags.REGISTRATION_FOR_EMAIL_CODE_USERNAME_TEXT_FIELD),
            textStyle = TextStyle.Default.copy(
                fontSize = LocalAppTheme.current.textSize1
            ),
            placeholder = {
                Text(
                    stringResource(R.string.username),
                    textAlign = TextAlign.Center,
                    fontSize = LocalAppTheme.current.textSize1
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            modifier = Modifier.testTag(NodeTags.REGISTRATION_FOR_EMAIL_CODE_PASSWORD_TEXT_FIELD),
            textStyle = TextStyle.Default.copy(
                fontSize = LocalAppTheme.current.textSize1
            ),
            placeholder = {
                Text(
                    stringResource(R.string.password),
                    textAlign = TextAlign.Center,
                    fontSize = LocalAppTheme.current.textSize1
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            modifier = Modifier.testTag(NodeTags.REGISTRATION_FOR_EMAIL_CODE_EMAIL_TEXT_FIELD),
            textStyle = TextStyle.Default.copy(
                fontSize = LocalAppTheme.current.textSize1
            ),
            placeholder = {
                Text(
                    "email",
                    textAlign = TextAlign.Center,
                    fontSize = LocalAppTheme.current.textSize1
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.registerForEmailCode(
                    username = username.value,
                    password = password.value,
                    email = email.value
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .testTag(NodeTags.REGISTRATION_FOR_EMAIL_CODE_REGISTER_BUTTON)
        ) {
            Text(
                text = stringResource(R.string.register),
                textAlign = TextAlign.Center,
                fontSize = LocalAppTheme.current.textSize1
            )
        }
    }

}