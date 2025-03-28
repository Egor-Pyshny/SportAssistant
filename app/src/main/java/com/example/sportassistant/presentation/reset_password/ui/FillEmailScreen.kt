package com.example.sportassistant.presentation.reset_password.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.auth.requests.ForgotPasswordRequest
import com.example.sportassistant.presentation.AuthRoutes
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledInput
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.reset_password.viewmodel.ResetPasswordViewModel
import org.koin.androidx.compose.get

@Composable
fun ResetPasswordEmailScreen(
    navController: NavController,
    viewModel: ResetPasswordViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by viewModel.uiState.collectAsState()
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
                top = 60.dp
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = stringResource(R.string.reset_password),
                style = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth(),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                StyledInput(
                    value = uiState.email,
                    onValueChange = {
                        var isError: Boolean = false
                        if (it.isNotEmpty()) {
                            isError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        }
                        viewModel.setUserMailError(isError)
                        viewModel.setEmail(it)
                    },
                    placeholder = stringResource(R.string.mail_input_placeholder),
                    leadingIcon = R.drawable.mail,
                    isError = uiState.userMailError,
                    supportingText = if (uiState.userMailError) {
                        stringResource(R.string.support_text_for_email)
                    } else null,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Column(
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            if (!uiState.userMailError && uiState.email.isNotEmpty()) {
                StyledButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = {
                        navController.navigate(AuthRoutes.ResetPasswordCode.route)
                        viewModel.getCode(
                            data = ForgotPasswordRequest(
                                email = uiState.email,
                            )
                        )
                    },
                    isEnabled = true,
                    trailingIcon = R.drawable.chevron_right,
                    trailingIconModifier = Modifier.padding(top = 1.dp)
                )
            } else {
                StyledOutlinedButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = { },
                    isEnabled = false,
                    trailingIcon = R.drawable.chevron_right,
                    trailingIconModifier = Modifier.padding(top = 1.dp)
                )
            }
        }
    }
}