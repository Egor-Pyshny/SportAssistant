package com.example.sportassistant.presentation.registration.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.components.GetDropdownTrailingIcon
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledInput
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.registration.domain.model.ProfileInfoUiState
import com.example.sportassistant.presentation.registration.domain.model.RegistrationUiState
import com.example.sportassistant.presentation.registration.viewmodel.SetProfileInfoViewModel
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationProfileScreen(
    viewModel: SetProfileInfoViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    onContinueRegistrationButtonClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    Column (
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
                top = 45.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.create_profile_text),
                    style = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth(),
                )
                Image(
                    modifier = Modifier.height(screenSizeProvider.getScreenDimensions().screenHeight*0.27f),
                    painter = painterResource(R.drawable.profile_screen),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                StyledInput(
                    value = uiState.sportType,
                    onValueChange = {
                        viewModel.setSportType(it)
                    },
                    placeholder = stringResource(R.string.sport_type_placeholder),
                    leadingIcon = R.drawable.sport_type,
                    onTrailingIconClick = {},
                    isError = false,
                    modifier = Modifier.fillMaxWidth()
                )
                StyledInput(
                    value = uiState.qualification,
                    onValueChange = {
                        viewModel.setQualification(it)
                    },
                    placeholder = stringResource(R.string.qualification_placeholder),
                    leadingIcon = R.drawable.qualification,
                    onTrailingIconClick = {},
                    isError = false,
                    modifier = Modifier.fillMaxWidth()
                )
                StyledInput(
                    value = uiState.address,
                    onValueChange = {
                        viewModel.setAddress(it)
                    },
                    placeholder = stringResource(R.string.address_placeholder),
                    leadingIcon = R.drawable.address,
                    onTrailingIconClick = {},
                    isError = false,
                    modifier = Modifier.fillMaxWidth()
                )
                StyledInput(
                    value = uiState.phoneNumber,
                    onValueChange = {
                        var temp = it.replace("_","")
                        if (temp.length <= 9) {
                            val newPhone = temp + "_".repeat(9-temp.length)
                            viewModel.setPhoneNumber(newPhone)
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
                ExposedDropdownMenuBox (
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = uiState.gender,
                        onValueChange = {},
                        placeholder = {
                            Text(
                                text = stringResource(R.string.gender_placeholder)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.gender),
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            GetDropdownTrailingIcon(expanded)
                        },
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        singleLine = true,
                        readOnly = true,
                    )

                    DropdownMenu (
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.exposedDropdownSize()
                    ) {
                        stringArrayResource(R.array.gender_options).forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = option,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                onClick = {
                                    viewModel.setGender(option)
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            if (isAllFilled(uiState)) {
                StyledButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = onContinueRegistrationButtonClick,
                    isEnabled = true,
                    trailingIcon = R.drawable.chevron_right,
                    trailingIconModifier = Modifier.padding(top = 1.dp),
                    modifier = Modifier.padding(bottom = 45.dp, top = 45.dp)
                )
            } else {
                StyledOutlinedButton(
                    text = stringResource(R.string.continue_button_text),
                    onClick = onContinueRegistrationButtonClick,
                    isEnabled = false,
                    trailingIcon = R.drawable.chevron_right,
                    trailingIconModifier = Modifier.padding(top = 1.dp),
                    modifier = Modifier.padding(bottom = 45.dp, top = 45.dp)
                )
            }
        }
    }
}

private fun isAllFilled(state: ProfileInfoUiState): Boolean {
//    return true
    return (state.gender.isNotEmpty()
            && state.sportType.isNotEmpty()
            && state.qualification.isNotEmpty()
            && state.address.isNotEmpty()
            && state.phoneNumber.length == 9)
}
