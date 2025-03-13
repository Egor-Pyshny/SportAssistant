package com.example.sportassistant.presentation.training_camps_day.ui

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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.data.schemas.training_camp_day.requests.TrainingCampDayUpdateRequest
import com.example.sportassistant.domain.model.TrainingCampDay
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.training_camps_calendar.viewmodel.TrainingCampsViewModel
import com.example.sportassistant.presentation.training_camps_day.domain.TrainingCampDayUiState
import com.example.sportassistant.presentation.training_camps_day.viewmodel.TrainingCampDayViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import java.time.format.DateTimeFormatter

@Composable
fun TrainingCampDayScreen(
    trainingCampsViewModel: TrainingCampsViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    campDayViewModel: TrainingCampDayViewModel = viewModel(),
) {
    val campUpdateDayResponse by trainingCampsViewModel.updateCampDayResponse.observeAsState()
    val campDayResponse by trainingCampsViewModel.getCampDayResponse.observeAsState()
    val uiState by campDayViewModel.uiState.collectAsState()
    val campUiState by trainingCampsViewModel.uiState.collectAsState()
    var prevState by remember { mutableStateOf<TrainingCampDayUiState>(
        TrainingCampDayUiState(
            notes = "",
            goals = "",
        )
    )
    }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (campDayResponse) {
            is ApiResponse.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ApiResponse.Success -> {
                val data = (campDayResponse as ApiResponse.Success<TrainingCampDay?>).data!!
                LaunchedEffect(Unit) {
                    campDayViewModel.setNotes(data.notes)
                    campDayViewModel.setGoals(data.goals)
                    prevState = TrainingCampDayUiState(notes = data.notes, goals = data.goals)
                }
                Card(
                    modifier = modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                ) {
                    StyledCardTextField(
                        value = data.campLocation,
                        label = R.string.add_competition_location,
                        onValueChange = { },
                        enabled = false,
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
                    ) {
                        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

                        val startDate = data.campStartDate.format(formatter)
                        val endDate = data.campEndDate.format(formatter)

                        StyledCardTextField(
                            value = "$startDate - $endDate",
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
                    HorizontalDivider(thickness = 2.dp)
                    StyledCardTextField(
                        value = uiState.goals,
                        label = R.string.add_camp_goals,
                        onValueChange = {
                            campDayViewModel.setGoals(it)
                        },
                        singleLine = false,
                        maxLines = 2,
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
                            campDayViewModel.setNotes(it)
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
                    if (isModified(uiState, prevState)) {
                        StyledButton(
                            text = stringResource(R.string.save_button_text),
                            onClick = {
                                trainingCampsViewModel.updateCampDay(
                                    campDay = TrainingCampDayUpdateRequest(
                                        id = data.id,
                                        goals = uiState.goals,
                                        notes = uiState.notes,
                                        date = data.date
                                    ),
                                    campId = campUiState.selectedCamp!!.id,
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
                    text = (campDayResponse as ApiResponse.Failure).errorMessage
                )
            }
            else -> {}
        }
    }

    when (campUpdateDayResponse) {
        is ApiResponse.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ApiResponse.Success -> {
            LaunchedEffect(Unit) {
                prevState = TrainingCampDayUiState(notes = uiState.notes, goals = uiState.goals)
                trainingCampsViewModel.clearUpdateDayResponse()
            }
        }
        else -> {}
    }
}

private fun isModified(state: TrainingCampDayUiState, prevState: TrainingCampDayUiState): Boolean {
    return (state.goals != prevState.goals
            || state.notes != prevState.notes)
}
