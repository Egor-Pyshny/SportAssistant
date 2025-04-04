package com.example.sportassistant.presentation.competition_day.ui

import androidx.compose.foundation.background
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
import androidx.compose.runtime.DisposableEffect
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
import com.example.sportassistant.data.schemas.competition.requests.CreateCompetitionRequest
import com.example.sportassistant.data.schemas.competition_day.requests.CompetitionDayUpdateRequest
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.CompetitionDay
import com.example.sportassistant.presentation.competition_add.domain.CompetitionUiState
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_day.domain.CompetitionDayUiState
import com.example.sportassistant.presentation.competition_day.viewmodel.CompetitionDayViewModel
import com.example.sportassistant.presentation.components.ErrorScreen
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.components.SingleButtonDialog
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@Composable
fun CompetitionDayScreen(
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    competitionDayViewModel: CompetitionDayViewModel = koinViewModel(),
) {
    val state = ApplicationState.getState()
    LaunchedEffect(Unit) {
        competitionDayViewModel.getCompetitionDay(
            competitionId = state.competition!!.id,
            day = state.competitionDay!!,
        )
    }
    val competitionUpdateDayResponse by competitionDayViewModel.updateCompetitionDayResponse.observeAsState()
    val competitionDayResponse by competitionDayViewModel.getCompetitionDayResponse.observeAsState()
    val uiState by competitionDayViewModel.uiState.collectAsState()
    val competitionUiState by competitionDayViewModel.uiState.collectAsState()
    var prevState by remember { mutableStateOf<CompetitionDayUiState>(
        CompetitionDayUiState(
            notes = "",
            result = "",
        ))
    }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (competitionDayResponse) {
            is ApiResponse.Loading -> {
                Loader()
            }
            is ApiResponse.Success -> {
                val data = (competitionDayResponse as ApiResponse.Success<CompetitionDay?>).data!!
                LaunchedEffect(Unit) {
                    competitionDayViewModel.setNotes(data.notes)
                    competitionDayViewModel.setResult(data.results)
                    prevState = CompetitionDayUiState(notes = data.notes, result = data.results)
                }
                Card(
                    modifier = modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                ) {
                    StyledCardTextField(
                        value = data.competitionName,
                        label = R.string.add_competition_title,
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

                        val startDate = data.competitionStartDate.format(formatter)
                        val endDate = data.competitionEndDate.format(formatter)

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
                        value = data.competitionLocation,
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
                    StyledCardTextField(
                        value = uiState.result,
                        label = R.string.add_competition_result,
                        onValueChange = {
                            competitionDayViewModel.setResult(it)
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
                            competitionDayViewModel.setNotes(it)
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
                                competitionDayViewModel.updateCompetitionDay(
                                    competitionDay = CompetitionDayUpdateRequest(
                                        id = data.id,
                                        result = uiState.result,
                                        notes = uiState.notes,
                                        date = data.date
                                    ),
                                    competitionId = state.competition!!.id,
                                )
                            },
                            isEnabled = true,
                            trailingIcon = R.drawable.save,
                            trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                            modifier = Modifier.padding(bottom = 25.dp, top = 35.dp)
                        )
                    } else {
                        StyledOutlinedButton(
                            text = stringResource(R.string.save_button_text),
                            onClick = {},
                            isEnabled = false,
                            trailingIcon = R.drawable.save,
                            trailingIconModifier = Modifier.padding(top = 1.dp, start = 10.dp),
                            modifier = Modifier.padding(bottom = 25.dp, top = 35.dp)
                        )
                    }
                }
            }
            is ApiResponse.Failure -> {
                ErrorScreen(competitionDayResponse as ApiResponse.Failure)
            }
            else -> {}
        }
    }

    when (competitionUpdateDayResponse) {
        is ApiResponse.Loading -> {
            Loader(Modifier.background(Color.White.copy(alpha = 0.7f)))
        }
        is ApiResponse.Success -> {
            LaunchedEffect(Unit) {
                prevState = CompetitionDayUiState(notes = uiState.notes, result = uiState.result)
                competitionDayViewModel.clearUpdate()
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

private fun isModified(state: CompetitionDayUiState, prevState: CompetitionDayUiState): Boolean {
    return (state.result != prevState.result
            || state.notes != prevState.notes)
}
