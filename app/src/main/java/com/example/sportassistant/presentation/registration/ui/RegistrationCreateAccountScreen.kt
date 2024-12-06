package com.example.sportassistant.presentation.registration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledInput
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.registration.domain.model.RegistrationUiState
import com.example.sportassistant.presentation.registration.viewmodel.RegistrationViewModel

@Composable
fun RegistrationCreateAccountScreen(
    viewModel: RegistrationViewModel,
    onContinueRegistrationButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    Column (
        modifier = modifier.fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = stringResource(R.string.create_account_text),
                style = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth(),
            )
            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                StyledInput(
                    value = uiState.userName,
                    onValueChange = {
                        if (it.length < 15) {
                            viewModel.setName(it)
                        }
                    },
                    placeholder = stringResource(R.string.name_input_placeholder),
                    leadingIcon = R.drawable.user_icon,
                    modifier = Modifier.fillMaxWidth()
                )
                StyledInput(
                    value = uiState.userSurname,
                    onValueChange = {
                        if (it.length < 15) {
                            viewModel.setSurname(it)
                        }
                    },
                    placeholder = stringResource(R.string.surname_input_placeholder),
                    leadingIcon = R.drawable.user_icon,
                    modifier = Modifier.fillMaxWidth()
                )
                StyledInput(
                    value = uiState.userMail,
                    onValueChange = {
                        var isError: Boolean = false
                        if (it.isNotEmpty()) {
                            isError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        }
                        viewModel.setUserMailError(isError)
                        viewModel.setMail(it)
                    },
                    placeholder = stringResource(R.string.mail_input_placeholder),
                    leadingIcon = R.drawable.mail,
                    isError = uiState.userMailError,
                    supportingText = if (uiState.userMailError) { stringResource(R.string.support_text_for_email) } else null,
                    modifier = Modifier.fillMaxWidth()
                )
                StyledInput(
                    value = uiState.userPassword,
                    onValueChange = {
                        var isError: Boolean = false
                        if (it.length in 1..7) {
                            isError = true
                        }
                        viewModel.setPasswordError(isError)
                        viewModel.setPassword(it)
                    },
                    placeholder = stringResource(R.string.password_input_placeholder),
                    leadingIcon = R.drawable.password_icon,
                    trailingIcon = if(uiState.passwordVisibility) R.drawable.visibility else R.drawable.visibility_off,
                    onTrailingIconClick = {
                        viewModel.setPasswordVisibility(!uiState.passwordVisibility)
                    },
                    visualTransformation = if (!uiState.passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
                    isError = uiState.userPasswordError,
                    supportingText = if (uiState.userPasswordError) { stringResource(R.string.support_text_for_password) } else null ,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Column(
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            if (isAllFilled(uiState)) {
                StyledButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = onContinueRegistrationButtonClick,
                    isEnabled = true,
                    trailingIcon = R.drawable.chevron_right,
                    trailingIconModifier = Modifier.padding(top = 1.dp)
                )
            } else {
                StyledOutlinedButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = onContinueRegistrationButtonClick,
                    isEnabled = false,
                    trailingIcon = R.drawable.chevron_right,
                    trailingIconModifier = Modifier.padding(top = 1.dp)
                )
            }
        }
    }
}

private fun isAllFilled(state: RegistrationUiState): Boolean {
    return true
    return (!state.userMailError &&
            !state.userPasswordError
            && state.userName.isNotEmpty()
            && state.userMail.isNotEmpty()
            && state.userPassword.isNotEmpty()
            && state.userSurname.isNotEmpty())
}

fun checkInputValues(viewModel: RegistrationViewModel): Boolean {
    val userNameError: Boolean = false
    val userSurnameError: Boolean = false
    val userMailError: Boolean = false
    val userPasswordError: Boolean = false

    return false
}