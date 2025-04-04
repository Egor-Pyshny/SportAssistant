package com.example.sportassistant.presentation.login.ui

import android.opengl.Visibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.auth.requests.LoginRequest
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledInput
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.login.domain.LogInUiState
import com.example.sportassistant.presentation.login.viewmodel.LogInViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LogInScreen(
    viewModel: LogInViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    onLogInButtonClick: () -> Unit = {},
    onForgotPasswordButtonClick: () -> Unit = {},
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val loginState by viewModel.loginResponse.observeAsState()
    val preferences: UserPreferencesRepository = get()
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
                text = stringResource(R.string.login_text),
                style = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth(),
            )
            Column (
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
                    supportingText = if (uiState.userMailError) { stringResource(R.string.support_text_for_email) } else null,
                    modifier = Modifier.fillMaxWidth()
                )
                StyledInput(
                    value = uiState.password,
                    onValueChange = {
                        viewModel.setPassword(it)
                    },
                    placeholder = stringResource(R.string.password_input_placeholder),
                    leadingIcon = R.drawable.password_icon,
                    trailingIcon = if(uiState.passwordVisibility) R.drawable.visibility else R.drawable.visibility_off,
                    onTrailingIconClick = {
                        viewModel.setPasswordVisibility(!uiState.passwordVisibility)
                    },
                    visualTransformation = if (!uiState.passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        text = stringResource(R.string.forgot_password_text),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.clickable {
                            onForgotPasswordButtonClick()
                        },
                        text = stringResource(R.string.forgot_password_link),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Color.Blue,
                        textAlign = TextAlign.Center
                    )
                }
            }
            if (loginState is ApiResponse.Failure) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    text = (loginState as ApiResponse.Failure).errorMessage
                )
            }
        }
        Column(
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            if (isAllFilled(uiState)) {
                StyledButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = {
                        viewModel.login(
                            LoginRequest(
                                email = uiState.email,
                                password = uiState.password,
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

    when (loginState) {
        is ApiResponse.Loading -> {
            Loader()
        }
        is ApiResponse.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            LaunchedEffect(Unit) {
                preferences.setIsLoggedIn(true)
                onLogInButtonClick()
            }
        }
        else -> { Loader() }
    }
}

private fun isAllFilled(state: LogInUiState): Boolean {
    return (!state.userMailError && state.email.isNotEmpty()
            && state.password.isNotEmpty())
}
