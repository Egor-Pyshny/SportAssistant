package com.example.sportassistant.presentation.ant_params_add.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.ant_params.requests.AnthropometricParamsCreateRequest
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.ant_params_add.domain.AnthropometricParamsUiState
import com.example.sportassistant.presentation.ant_params_add.viewmodel.AnthropometricParamsAddViewModel
import com.example.sportassistant.presentation.components.DatePickerHeadline
import com.example.sportassistant.presentation.components.DecimalFormatter
import com.example.sportassistant.presentation.components.DecimalInputVisualTransformation
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnthropometricParamsAddScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    anthropometricParamsAddViewModel: AnthropometricParamsAddViewModel = koinViewModel(),
) {
    val uiState by anthropometricParamsAddViewModel.uiState.collectAsState()
    val anthropometricParamsAddState by anthropometricParamsAddViewModel.anthropometricParamsAddResponse.observeAsState()
    var missingDate by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Input,
    )
    val decimalFormatter = DecimalFormatter()
    var showDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        showDialog = true
                        missingDate = false
                    }
            ) {
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

                val date = datePickerState.selectedDateMillis?.let {
                    val date = LocalDate.ofInstant(
                        Instant.ofEpochMilli(it),
                        ZoneId.systemDefault()
                    )
                    anthropometricParamsAddViewModel.setDate(date)
                    date.format(formatter)
                } ?: ""

                StyledCardTextField(
                    value = date,
                    label = R.string.add_competition_date,
                    onValueChange = { },
                    enabled = false,
                    modifier = Modifier.padding(
                        start = 15.dp,
                        end = 15.dp,
                        top = 15.dp,
                        bottom = 6.dp,
                    )
                )
            }
            if (showDialog) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (
                                datePickerState.selectedDateMillis != null
                            ) {
                                showDialog = false
                                missingDate = false
                            } else {
                                missingDate = true
                            }
                        }) {
                            Text(stringResource(R.string.confirm))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                            datePickerState.selectedDateMillis = System.currentTimeMillis()
                            anthropometricParamsAddViewModel.setDate(LocalDate.now())
                        }) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        modifier = Modifier,
                        showModeToggle = false,
                        title = {},
                        headline = {
                            DatePickerHeadline(
                                selectedDateMillis = datePickerState.selectedDateMillis,
                                dateFormatter = DatePickerDefaults.dateFormatter(),
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    end = 12.dp,
                                    bottom = 12.dp,
                                ),
                                datePlaceholder = { Text(text = stringResource(R.string.date_placeholder)) },
                            )
                        },
                    )
                    if (missingDate) {
                        val text = stringResource(R.string.missing_ofp_date_error)
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(text = text, color = Color.Red)
                        }
                    }
                }
            }
            HorizontalDivider(thickness = 2.dp)
            StyledCardTextField(
                value = uiState.weight,
                label = R.string.ant_param_weight,
                onValueChange = {
                    val formatedText = decimalFormatter.cleanup(it)
                    if (formatedText.toFloatOrNull() != null) {
                        anthropometricParamsAddViewModel.setWeight(formatedText)
                    }
                    if (it == "") {
                        anthropometricParamsAddViewModel.setWeight("")
                    }
                },
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 6.dp,
                ),
                keyboardType = KeyboardType.Number,
                visualTransformation = DecimalInputVisualTransformation(decimalFormatter)
            )
            HorizontalDivider(thickness = 2.dp)
            StyledCardTextField(
                value = uiState.height,
                label = R.string.ant_param_height,
                onValueChange = {
                    val formatedText = decimalFormatter.cleanup(it)
                    if (formatedText.toFloatOrNull() != null) {
                        anthropometricParamsAddViewModel.setHeight(formatedText)
                    }
                    if (it == "") {
                        anthropometricParamsAddViewModel.setWeight("")
                    }
                },
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 6.dp,
                ),
                keyboardType = KeyboardType.Number,
                visualTransformation = DecimalInputVisualTransformation(decimalFormatter)
            )
            HorizontalDivider(thickness = 2.dp)
            StyledCardTextField(
                value = uiState.chestCircumference,
                label = R.string.ant_param_chest_сircumference,
                onValueChange = {
                    val formatedText = decimalFormatter.cleanup(it)
                    if (formatedText.toFloatOrNull() != null) {
                        anthropometricParamsAddViewModel.setChestCircumference(formatedText)
                    }
                    if (it == "") {
                        anthropometricParamsAddViewModel.setWeight("")
                    }
                },
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 15.dp,
                ),
                keyboardType = KeyboardType.Number,
                visualTransformation = DecimalInputVisualTransformation(decimalFormatter)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (isAllFilled(uiState)) {
                StyledButton(
                    text = stringResource(R.string.save_button_text),
                    onClick = {
                        anthropometricParamsAddViewModel.addAnthropometricParams(
                            AnthropometricParamsCreateRequest(
                                date = uiState.date!!,
                                weight = uiState.weight.toFloat(),
                                height = uiState.height.toFloat(),
                                chestCircumference = uiState.chestCircumference.toFloat(),
                            )
                        )
                    },
                    isEnabled = true,
                    trailingIcon = R.drawable.save,
                    trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                    modifier = Modifier.padding(bottom = 25.dp, top = 45.dp)
                )
            } else {
                StyledOutlinedButton(
                    text = stringResource(R.string.save_button_text),
                    onClick = {},
                    isEnabled = false,
                    trailingIcon = R.drawable.save,
                    trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                    modifier = Modifier.padding(bottom = 25.dp, top = 45.dp)
                )
            }
        }
    }

    when (anthropometricParamsAddState) {
        is ApiResponse.Loading -> {
            Loader()
        }
        is ApiResponse.Success -> {
            Loader()
            LaunchedEffect(Unit) {
                navController.navigate(HomeRoutes.AnthropometricParams.route) {
                    popUpTo(HomeRoutes.AnthropometricParams.route) {
                        inclusive = true
                    }
                }
            }
        }
        else -> {}
    }
}

private fun isAllFilled(state: AnthropometricParamsUiState): Boolean {
    return (state.weight.toFloatOrNull() != null
            && state.height.toFloatOrNull() != null
            && state.date != null
            && state.chestCircumference.toFloatOrNull() != null)
}