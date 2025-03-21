package com.example.sportassistant.presentation.med_examination_info.ui

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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.med_examination.requests.MedExaminationCreateRequest
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.model.MedExamination
import com.example.sportassistant.presentation.components.DatePickerHeadline
import com.example.sportassistant.presentation.components.DecimalFormatter
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.med_examination.viewmodel.MedExaminationViewModel
import com.example.sportassistant.presentation.med_examination_add.domain.MedExaminationUiState
import com.example.sportassistant.presentation.med_examination_add.viewmodel.MedExaminationAddViewModel
import com.example.sportassistant.presentation.med_examination_info.viewmodel.MedExaminationInfoViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
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
fun MedExaminationInfoScreen(
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    medExaminationInfoViewModel: MedExaminationInfoViewModel = koinViewModel(),
) {
    val state = ApplicationState.getState()
    LaunchedEffect(Unit) {
        medExaminationInfoViewModel.getMedExaminationInfo(state.medExamination!!.id)
    }
    val medExaminationInfoResponse by medExaminationInfoViewModel.getMedExaminationInfoResponse.observeAsState()
    val medExaminationUpdateResponse by medExaminationInfoViewModel.updateMedExaminationResponse.observeAsState()
    var prevState by remember { mutableStateOf<MedExamination?>(null) }
    val uiState by medExaminationInfoViewModel.uiState.collectAsState()
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
        when (medExaminationInfoResponse) {
            is ApiResponse.Loading -> {
                Loader()
            }
            is ApiResponse.Success -> {
                LaunchedEffect(Unit) {
                    val medExaminationData = (medExaminationInfoResponse as ApiResponse.Success<MedExamination?>).data!!
                    prevState = medExaminationData
                    medExaminationInfoViewModel.setDate(medExaminationData.date)
                    medExaminationInfoViewModel.setMethods(medExaminationData.methods)
                    medExaminationInfoViewModel.setInstitution(medExaminationData.institution)
                    medExaminationInfoViewModel.setRecommendations(medExaminationData.recommendations)
                    val dateMillis = medExaminationData.date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
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
                            medExaminationInfoViewModel.setDate(date)
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
                                    medExaminationInfoViewModel.setDate(LocalDate.now())
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
                        value = uiState.institution,
                        label = R.string.institution_placeholder,
                        onValueChange = {
                            medExaminationInfoViewModel.setInstitution(it)
                        },
                        maxLines = 3,
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 6.dp,
                        ),
                    )
                    HorizontalDivider(thickness = 2.dp)
                    StyledCardTextField(
                        value = uiState.methods,
                        label = R.string.methods_label,
                        onValueChange = {
                            medExaminationInfoViewModel.setMethods(it)
                        },
                        maxLines = 3,
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 6.dp,
                        ),
                    )
                    HorizontalDivider(thickness = 2.dp)
                    StyledCardTextField(
                        value = uiState.recommendations,
                        label = R.string.recommendations_placeholder,
                        onValueChange = {
                            medExaminationInfoViewModel.setRecommendations(it)
                        },
                        maxLines = 5,
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 15.dp,
                        ),
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (isAllFilled(uiState) && isModified(uiState, prevState!!)) {
                        StyledButton(
                            text = stringResource(R.string.save_button_text),
                            onClick = {
                                medExaminationInfoViewModel.updateMedExamination(
                                    data = MedExaminationCreateRequest(
                                        date = uiState.date!!,
                                        institution = uiState.institution,
                                        methods = uiState.methods,
                                        recommendations = uiState.recommendations,
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
                Text(
                    text = (medExaminationInfoResponse as ApiResponse.Failure).errorMessage
                )
            }
            else -> {}
        }
    }

    when (medExaminationUpdateResponse) {
        is ApiResponse.Loading -> {
            Loader()
        }
        is ApiResponse.Success -> {
            LaunchedEffect(Unit) {
                val newState = MedExamination(
                    id = prevState!!.id,
                    date = uiState.date!!,
                    institution = uiState.institution,
                    methods = uiState.methods,
                    recommendations = uiState.recommendations,
                )
                prevState = newState
                medExaminationInfoViewModel.clearUpdateResponse()
            }
        }
        else -> {}
    }
}

private fun isAllFilled(state: MedExaminationUiState): Boolean {
    return (state.institution.isNotEmpty()
            && state.methods.isNotEmpty()
            && state.recommendations.isNotEmpty()
            && state.date != null)
}

private fun isModified(state: MedExaminationUiState, prevState: MedExamination): Boolean {
    return (state.date != prevState.date
            || state.institution != prevState.institution
            || state.methods != prevState.methods
            || state.recommendations != prevState.recommendations)
}