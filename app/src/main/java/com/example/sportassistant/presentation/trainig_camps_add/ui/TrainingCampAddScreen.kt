package com.example.sportassistant.presentation.trainig_camps_add.ui

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
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
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
import androidx.navigation.NavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.training_camps.requests.CreateTrainingCampRequest
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.components.DateRangePickerHeadline
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.trainig_camps_add.domain.TrainingCampUiState
import com.example.sportassistant.presentation.trainig_camps_add.viewmodel.TrainingCampAddViewModel
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
fun TrainingCampAddScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    campAddViewModel: TrainingCampAddViewModel = koinViewModel(),
) {
    val uiState by campAddViewModel.uiState.collectAsState()
    val campAddState by campAddViewModel.campAddResponse.observeAsState()
    var missingDate by remember { mutableStateOf(false) }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = System.currentTimeMillis(),
        initialSelectedEndDateMillis = null,
        initialDisplayMode = DisplayMode.Input,
    )

    var showDialog by remember { mutableStateOf(false) }
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
            StyledCardTextField(
                value = uiState.location,
                label = R.string.add_competition_location,
                onValueChange = {
                    campAddViewModel.setLocation(it)
                },
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 6.dp,
                )
            )
            HorizontalDivider(thickness = 2.dp)
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

                val startDate = dateRangePickerState.selectedStartDateMillis?.let { it ->
                    val date = LocalDate.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
                    campAddViewModel.setStartDate(date)
                    date.format(formatter)
                } ?: ""

                val endDate = dateRangePickerState.selectedEndDateMillis?.let {
                    val date = LocalDate.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
                    campAddViewModel.setEndDate(date)
                    date.format(formatter)
                } ?: ""

                StyledCardTextField(
                    value = "$startDate - $endDate",
                    label = R.string.add_camps_date,
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
                                dateRangePickerState.selectedEndDateMillis != null
                                && dateRangePickerState.selectedStartDateMillis != null
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
                            dateRangePickerState.setSelection(
                                startDateMillis = System.currentTimeMillis(),
                                endDateMillis = null,
                            )
                            campAddViewModel.setStartDate(LocalDate.now())
                            campAddViewModel.setEndDate(null)
                        }) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                ) {
                    DateRangePicker(
                        state = dateRangePickerState,
                        title = {},
                        modifier = Modifier,
                        showModeToggle = false,
                        headline = {
                            DateRangePickerHeadline(
                                selectedStartDateMillis = dateRangePickerState.selectedStartDateMillis,
                                selectedEndDateMillis = dateRangePickerState.selectedEndDateMillis,
                                dateFormatter = DatePickerDefaults.dateFormatter(),
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    end = 12.dp,
                                    bottom = 12.dp
                                ),
                                startDatePlaceholder = { Text(text = stringResource(R.string.start_date)) },
                                endDatePlaceholder = { Text(text = stringResource(R.string.end_date)) },
                                datesDelimiter = { Text(text = "-") }
                            )
                        },
                    )
                    if (missingDate) {
                        val text =
                            if (dateRangePickerState.selectedStartDateMillis == null)
                                stringResource(R.string.missing_start_date_error)
                            else if (dateRangePickerState.selectedEndDateMillis == null)
                                stringResource(R.string.missing_end_date_error)
                            else
                                stringResource(R.string.missing_date_error)
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
                value = uiState.goals,
                label = R.string.add_camp_goals,
                onValueChange = {
                    campAddViewModel.setGoals(it)
                },
                maxLines = 5,
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 6.dp,
                )
            )
            HorizontalDivider(thickness = 2.dp)
            StyledCardTextField(
                value = uiState.notes,
                label = R.string.add_competition_notes,
                onValueChange = {
                    campAddViewModel.setNotes(it)
                },
                singleLine = false,
                maxLines = 5,
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 15.dp,
                )
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (isAllFilled(uiState)) {
                StyledButton(
                    text = stringResource(R.string.save_button_text),
                    onClick = {
                        campAddViewModel.createCamp(
                            CreateTrainingCampRequest(
                                startDate = uiState.startDate!!,
                                endDate = uiState.endDate!!,
                                goals = uiState.goals,
                                notes = uiState.notes,
                                location = uiState.location,
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

    when (campAddState) {
        is ApiResponse.Loading -> {
            Loader()
        }
        is ApiResponse.Success -> {
            Loader()
            LaunchedEffect(Unit) {
                navController.navigate(HomeRoutes.TrainingCamps.route) {
                    popUpTo(HomeRoutes.TrainingCamps.route) {
                        inclusive = true
                    }
                }
            }
        }
        else -> {}
    }
}

private fun isAllFilled(state: TrainingCampUiState): Boolean {
    return (state.goals.isNotEmpty()
            && state.location.isNotEmpty()
            && state.startDate != null
            && state.endDate != null)
}