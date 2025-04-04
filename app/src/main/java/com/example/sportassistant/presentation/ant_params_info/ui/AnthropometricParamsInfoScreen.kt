package com.example.sportassistant.presentation.ant_params_info.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.ant_params.requests.AnthropometricParamsCreateRequest
import com.example.sportassistant.data.schemas.ofp_results.requests.OFPResultsCreateRequest
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.model.AnthropometricParams
import com.example.sportassistant.domain.model.CategoryModel
import com.example.sportassistant.domain.model.OFPResult
import com.example.sportassistant.presentation.ant_params.viewmodel.AnthropometricParamsViewModel
import com.example.sportassistant.presentation.ant_params_add.domain.AnthropometricParamsUiState
import com.example.sportassistant.presentation.ant_params_add.viewmodel.AnthropometricParamsAddViewModel
import com.example.sportassistant.presentation.ant_params_info.viewmodel.AnthropometricParamsInfoViewModel
import com.example.sportassistant.presentation.components.DatePickerHeadline
import com.example.sportassistant.presentation.components.DecimalFormatter
import com.example.sportassistant.presentation.components.DecimalInputVisualTransformation
import com.example.sportassistant.presentation.components.ErrorScreen
import com.example.sportassistant.presentation.components.GetDropdownTrailingIcon
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.SingleButtonDialog
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.ofp_result_add.domain.OFPResultModelUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.getCategoryText
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnthropometricParamsInfoScreen(
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    anthropometricParamsInfoViewModel: AnthropometricParamsInfoViewModel = koinViewModel(),
) {
    val state = ApplicationState.getState()
    LaunchedEffect(Unit) {
        anthropometricParamsInfoViewModel.getAnthropometricParamsInfo(state.antParams!!.id)
    }
    val anthropometricParamsInfoResponse by anthropometricParamsInfoViewModel.getAnthropometricParamsInfoResponse.observeAsState()
    val anthropometricParamsUpdateResponse by anthropometricParamsInfoViewModel.updateAnthropometricParamsResponse.observeAsState()
    var prevState by remember { mutableStateOf<AnthropometricParams?>(null) }
    val uiState by anthropometricParamsInfoViewModel.uiState.collectAsState()
    var missingDate by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Input,
    )
    val decimalFormatter = DecimalFormatter()
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (anthropometricParamsInfoResponse) {
            is ApiResponse.Loading -> {
                Loader()
            }
            is ApiResponse.Success -> {
                LaunchedEffect(Unit) {
                    val anthropometricParamsData = (anthropometricParamsInfoResponse as ApiResponse.Success<AnthropometricParams?>).data!!
                    prevState = anthropometricParamsData
                    val regex = Regex("^(.*)\\.0+$")
                    var matchResult = regex.matchEntire(anthropometricParamsData.weight.toString())
                    val newWeight = matchResult?.groupValues?.get(1)
                    matchResult = regex.matchEntire(anthropometricParamsData.height.toString())
                    val newHeight = matchResult?.groupValues?.get(1)
                    matchResult = regex.matchEntire(anthropometricParamsData.chestCircumference.toString())
                    val newChestCircumference = matchResult?.groupValues?.get(1)
                    anthropometricParamsInfoViewModel.setDate(anthropometricParamsData.date)
                    anthropometricParamsInfoViewModel.setWeight(newWeight ?: anthropometricParamsData.weight.toString())
                    anthropometricParamsInfoViewModel.setHeight(newHeight ?: anthropometricParamsData.height.toString())
                    anthropometricParamsInfoViewModel.setChestCircumference(newChestCircumference ?: anthropometricParamsData.chestCircumference.toString())
                    val dateMillis = anthropometricParamsData.date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                    datePickerState.selectedDateMillis = dateMillis
                }
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
                            anthropometricParamsInfoViewModel.setDate(date)
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
                                    anthropometricParamsInfoViewModel.setDate(LocalDate.now())
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
                            if (formatedText.toFloatOrNull() != null){
                                anthropometricParamsInfoViewModel.setWeight(formatedText)
                            } else if (formatedText.toFloatOrNull() == null && it == "") {
                                anthropometricParamsInfoViewModel.setWeight("")
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
                            if (formatedText.toFloatOrNull() != null){
                                anthropometricParamsInfoViewModel.setHeight(formatedText)
                            } else if (formatedText.toFloatOrNull() == null && it == "") {
                                anthropometricParamsInfoViewModel.setHeight("")
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
                            if (formatedText.toFloatOrNull() != null){
                                anthropometricParamsInfoViewModel.setChestCircumference(formatedText)
                            } else if (formatedText.toFloatOrNull() == null && it == "") {
                                anthropometricParamsInfoViewModel.setChestCircumference("")
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
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (isAllFilled(uiState) && isModified(uiState, prevState!!)) {
                        StyledButton(
                            text = stringResource(R.string.save_button_text),
                            onClick = {
                                anthropometricParamsInfoViewModel.updateAnthropometricParams(
                                    data = AnthropometricParamsCreateRequest(
                                        date = uiState.date!!,
                                        weight = uiState.weight.toFloat(),
                                        height = uiState.height.toFloat(),
                                        chestCircumference = uiState.chestCircumference.toFloat(),
                                    ),
                                    paramsId = prevState!!.id,
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
            is ApiResponse.Failure -> {
                ErrorScreen(anthropometricParamsInfoResponse as ApiResponse.Failure)
            }
            else -> {}
        }
    }

    when (anthropometricParamsUpdateResponse) {
        is ApiResponse.Loading -> {
            Loader(Modifier.background(Color.White.copy(alpha = 0.7f)))
        }
        is ApiResponse.Success -> {
            LaunchedEffect(Unit) {
                val newState = AnthropometricParams(
                    id = prevState!!.id,
                    date = uiState.date!!,
                    weight = uiState.weight.toFloat(),
                    height = uiState.height.toFloat(),
                    chestCircumference = uiState.chestCircumference.toFloat(),
                )
                prevState = newState
                anthropometricParamsInfoViewModel.clearUpdateResponse()
            }
        }
        is ApiResponse.Failure -> {
            var showErrorDialog by remember { mutableStateOf(false) }
            SingleButtonDialog(
                showDialog = showErrorDialog,
                onDismiss = { showErrorDialog = false },
                title = stringResource(R.string.error_notification_title),
                message = stringResource(R.string.update_error_notification_text)
            )
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

private fun isModified(state: AnthropometricParamsUiState, prevState: AnthropometricParams): Boolean {
    if (state.date != prevState.date) {
        return true
    }
    val regex = Regex("^(.*)\\.0+$")

    var prevMatchResult = regex.matchEntire(prevState.weight.toString())
    val prevWeight = prevMatchResult?.groupValues?.get(1) ?: prevState.weight.toString()
    var currentMatchResult = regex.matchEntire(state.weight)
    val currentWeight = currentMatchResult?.groupValues?.get(1) ?: state.weight
    if (currentWeight != prevWeight && currentWeight.endsWith('.')) {
        val temp = currentWeight.dropLast(1)
        return temp != prevWeight
    }

    prevMatchResult = regex.matchEntire(prevState.height.toString())
    val prevHeight = prevMatchResult?.groupValues?.get(1) ?: prevState.height.toString()
    currentMatchResult = regex.matchEntire(state.height)
    val currentHeight = currentMatchResult?.groupValues?.get(1) ?: state.height
    if (currentHeight != prevHeight && currentHeight.endsWith('.')) {
        val temp = currentHeight.dropLast(1)
        return temp != prevHeight
    }

    prevMatchResult = regex.matchEntire(prevState.chestCircumference.toString())
    val prevChestCircumference = prevMatchResult?.groupValues?.get(1) ?: prevState.chestCircumference.toString()
    currentMatchResult = regex.matchEntire(state.chestCircumference)
    val currentChestCircumference = currentMatchResult?.groupValues?.get(1) ?: state.chestCircumference
    if (currentChestCircumference != prevChestCircumference && currentChestCircumference.endsWith('.')) {
        val temp = currentChestCircumference.dropLast(1)
        return temp != prevChestCircumference
    }

    return (prevChestCircumference != currentChestCircumference
            || prevHeight != currentHeight
            || prevWeight != currentWeight)
}