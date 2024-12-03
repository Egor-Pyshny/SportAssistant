package com.example.sportassistant.presentation.registration.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledInput
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.registration.domain.model.RegistrationUiState
import com.example.sportassistant.presentation.registration.viewmodel.RegistrationViewModel


@Composable
fun RegistrationCoachScreen(
    viewModel: RegistrationViewModel,
    onFinishRegistrationButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    Column (
        modifier = modifier.fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.create_coach_text),
                style = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth(),
            )
            Image(
                modifier = Modifier.padding(top = 20.dp),
                painter = painterResource(R.drawable.placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                StyledInput(
                    value = uiState.coachFIO,
                    onValueChange = {
                        viewModel.setCoachFIO(it)
                    },
                    placeholder = stringResource(R.string.coach_name_placeholder),
                    leadingIcon = R.drawable.user_icon,
                    onTrailingIconClick = {},
                    isError = false,
                    modifier = Modifier.fillMaxWidth()
                )
                StyledInput(
                    value = uiState.coachPhone,
                    onValueChange = {
                        var temp = it.replace("_","")
                        if (temp.length <= 9) {
                            val newPhone = temp + "_".repeat(9-temp.length)
                            viewModel.setCoachPhone(newPhone)
                        }
                    },
                    placeholder = "__) ___-__-__",
                    leadingIcon = R.drawable.phone,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    visualTransformation = PhoneVisualTransformation(
                        mask = "XX) XXX-XX-XX",
                        maskNumber = 'X',
                    ),
                    prefix = "+375 (",
                    modifier = Modifier.fillMaxWidth()
                )
                StyledInput(
                    value = uiState.coachInstitution,
                    onValueChange = {
                        viewModel.setCoachInstitution(it)
                    },
                    placeholder = stringResource(R.string.coach_institution_placeholder),
                    leadingIcon = R.drawable.institution,
                    onTrailingIconClick = {},
                    isError = false,
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
                    onClick = onFinishRegistrationButtonClick,
                    isEnabled = true,
                    trailingIcon = R.drawable.chevron_right,
                    trailingIconModifier = Modifier.padding(top = 1.dp)
                )
            } else {
                StyledOutlinedButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = onFinishRegistrationButtonClick,
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
    return (state.coachInstitution.isNotEmpty()
            && state.coachFIO.isNotEmpty()
            && state.coachPhone.length == 9)
}
