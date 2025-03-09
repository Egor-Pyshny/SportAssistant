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
import com.example.sportassistant.data.schemas.competition_day.requests.CompetitionDayUpdateRequest
import com.example.sportassistant.data.schemas.competition_result.requests.CompetitionResultUpdateRequest
import com.example.sportassistant.domain.model.CompetitionDay
import com.example.sportassistant.domain.model.CompetitionResult
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_day.domain.CompetitionDayUiState
import com.example.sportassistant.presentation.competition_day.domain.CompetitionResultUiState
import com.example.sportassistant.presentation.competition_day.viewmodel.CompetitionResultViewModel
import com.example.sportassistant.presentation.components.StyledButton
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.StyledOutlinedButton
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import java.time.format.DateTimeFormatter

@Composable
fun CompetitionResultScreen(
    competitionViewModel: CompetitionViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    competitionDayViewModel: CompetitionResultViewModel = viewModel(),
) {
    val competitionUpdateResultResponse by competitionViewModel.updateCompetitionResultResponse.observeAsState()
    val competitionResultResponse by competitionViewModel.getCompetitionResultResponse.observeAsState()
    val uiState by competitionDayViewModel.uiState.collectAsState()
    val competitionUiState by competitionViewModel.uiState.collectAsState()
    var prevState by remember { mutableStateOf<CompetitionResultUiState>(
        CompetitionResultUiState(
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
        when (competitionResultResponse) {
            is ApiResponse.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ApiResponse.Success -> {
                val data = (competitionResultResponse as ApiResponse.Success<CompetitionResult?>).data!!
                LaunchedEffect(Unit) {
                    competitionDayViewModel.setNotes(data.notes)
                    competitionDayViewModel.setResult(data.results)
                    prevState = CompetitionResultUiState(notes = data.notes, result = data.results)
                }
                Card(
                    modifier = modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                ) {
                    StyledCardTextField(
                        value = competitionUiState.selectedCompetition!!.name,
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

                        val startDate = competitionUiState.selectedCompetition!!.startDate.format(formatter)
                        val endDate = competitionUiState.selectedCompetition!!.endDate.format(formatter)

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
                        value = competitionUiState.selectedCompetition!!.location,
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
                                competitionViewModel.updateCompetitionResult(
                                    competitionResult = CompetitionResultUpdateRequest(
                                        id = data.id,
                                        result = uiState.result,
                                        notes = uiState.notes,
                                    ),
                                    competitionId = competitionUiState.selectedCompetition!!.id,
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
                    text = (competitionResultResponse as ApiResponse.Failure).errorMessage
                )
            }
            else -> {}
        }
    }

    when (competitionUpdateResultResponse) {
        is ApiResponse.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ApiResponse.Success -> {
            LaunchedEffect(Unit) {
                prevState = CompetitionResultUiState(notes = uiState.notes, result = uiState.result)
                competitionViewModel.clearUpdateResultResponse()
            }
        }
        else -> {}
    }
}

private fun isModified(state: CompetitionResultUiState, prevState: CompetitionResultUiState): Boolean {
    return (state.result != prevState.result
            || state.notes != prevState.notes)
}
