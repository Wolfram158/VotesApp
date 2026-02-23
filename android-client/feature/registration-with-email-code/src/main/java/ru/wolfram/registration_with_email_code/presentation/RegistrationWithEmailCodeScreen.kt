package ru.wolfram.registration_with_email_code.presentation

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.wolfram.common.R
import ru.wolfram.common.presentation.test.NodeTags
import ru.wolfram.registration_with_email_code.domain.model.RegistrationWithEmailCodeState
import ru.wolfram.common.presentation.theme.LocalAppTheme

@Composable
internal fun RegistrationWithEmailCodeScreen(
    viewModel: RegistrationWithEmailCodeViewModel,
    username: String,
    email: String,
    password: String,
    onSuccessfulRegistration: () -> Unit
) {
    val state = viewModel.state.collectAsState(RegistrationWithEmailCodeState.Initial)
    val code = remember { mutableStateOf("") }

    if (state.value is RegistrationWithEmailCodeState.Success) {
        onSuccessfulRegistration()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.value is RegistrationWithEmailCodeState.Failure) {
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
            modifier = Modifier.testTag(NodeTags.REGISTRATION_WITH_EMAIL_CODE_CODE_TEXT_FIELD),
            textStyle = TextStyle.Default.copy(
                fontSize = LocalAppTheme.current.textSize1,
                textAlign = TextAlign.Center
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.code),
                    fontSize = LocalAppTheme.current.textSize1,
                    textAlign = TextAlign.Center
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.registerWithEmailCode(
                    email = email,
                    code = code.value,
                    username = username,
                    password = password
                )
            },
            modifier = Modifier.testTag(NodeTags.REGISTRATION_WITH_EMAIL_CODE_CONFIRM_BUTTON)
        ) {
            Text(
                text = stringResource(R.string.confirm),
                fontSize = LocalAppTheme.current.textSize1,
                textAlign = TextAlign.Center
            )
        }
    }
}