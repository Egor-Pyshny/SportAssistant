package com.example.sportassistant.presentation.reset_password.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.auth.requests.ChangePasswordRequest
import com.example.sportassistant.data.schemas.auth.requests.LoginRequest
import com.example.sportassistant.data.schemas.auth.requests.ResendCodeRequest
import com.example.sportassistant.presentation.AuthRoutes
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledInput
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.login.domain.LogInUiState
import com.example.sportassistant.presentation.login.viewmodel.LogInViewModel
import com.example.sportassistant.presentation.reset_password.domain.ResetPasswordUiState
import com.example.sportassistant.presentation.reset_password.viewmodel.ResetPasswordViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    viewModel: ResetPasswordViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val resetState by viewModel.changePasswordResponse.observeAsState()
    var showError by remember { mutableStateOf(false) }
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
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
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
                StyledInput(
                    value = uiState.password,
                    onValueChange = {
                        viewModel.setPassword(it)
                    },
                    placeholder = stringResource(R.string.repeat_password_input_placeholder),
                    leadingIcon = R.drawable.password_icon,
                    trailingIcon = if(uiState.passwordVisibility) R.drawable.visibility else R.drawable.visibility_off,
                    onTrailingIconClick = {
                        viewModel.setPasswordVisibility(!uiState.passwordVisibility)
                    },
                    visualTransformation = if (!uiState.passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
                    modifier = Modifier.fillMaxWidth()
                )

            }
            Column(
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 150.dp).padding(top = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (resetState is ApiResponse.Failure) {
                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        text = (resetState as ApiResponse.Failure).errorMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
                if (showError) {
                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        text = stringResource(R.string.passwords_doesnt_match),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Column(
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            if (isAllFilled(uiState)) {
                StyledButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = {
                        if (uiState.password != uiState.repeatPassword) {
                            viewModel.changePassword(
                                data = ChangePasswordRequest(
                                    email = uiState.email,
                                    password = uiState.password,
                                )
                            )
                        } else {
                            showError = true
                        }
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

    when (resetState) {
        is ApiResponse.Loading -> {
            Loader(Modifier.background(Color.White.copy(alpha = 0.7f)))
        }
        is ApiResponse.Success -> {
            Loader(Modifier.background(Color.White.copy(alpha = 0.7f)))
            LaunchedEffect(Unit) {
                navController.navigate(AuthRoutes.LogIn.route){
                    popUpTo(0)
                }
            }
        }
        else -> {}
    }
}

private fun isAllFilled(state: ResetPasswordUiState): Boolean {
    return (state.password.isNotEmpty())
}