package com.example.sportassistant.presentation.train_diary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.components.RowScrollWheel
import com.example.sportassistant.presentation.components.StyledCardTextField
import com.example.sportassistant.presentation.components.TimeScrollWheel
import com.example.sportassistant.presentation.train_diary.viewmodel.TrainDiaryViewModel
import com.example.sportassistant.presentation.train_diary.viewmodel.TrainViewModel
import org.koin.androidx.compose.get
import java.time.LocalTime

@Composable
fun TrainScreen(
    type: PreparationType,
    diaryViewModel: TrainDiaryViewModel,
    trainViewModel: TrainViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by trainViewModel.uiState.collectAsState()
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
                .fillMaxWidth().padding(top = 20.dp),
            shape = MaterialTheme.shapes.large,
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 25.dp,
                    bottom = 10.dp
                ),
                text = stringResource(R.string.wish_to_train_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            RowScrollWheel(
                items = (1..10).map { "$it" },
                initialState = uiState.wishToTrain,
                onSelectedChanges = { index ->
                    trainViewModel.setWishToTrain(index)
                }
            )
            HorizontalDivider(thickness = 2.dp)
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 25.dp,
                    bottom = 10.dp
                ),
                text = stringResource(R.string.self_being_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            RowScrollWheel(
                items = (1..10).map { "$it" },
                initialState = uiState.selfBeing,
                onSelectedChanges = { index ->
                    trainViewModel.setSelfBeing(index)
                }
            )
            HorizontalDivider(thickness = 2.dp)
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 10.dp,
                    bottom = 10.dp
                ),
                text = stringResource(R.string.train_start_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            Row (
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 25.dp
                ).background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.large,
                ).fillMaxWidth(),
            ) {
                TimeScrollWheel (
                    hours = (0..23).map { "%02d".format(it) },
                    minutes = (0..59).map { "%02d".format(it) },
                    seconds = (0..59).map { "%02d".format(it) },
                    initialTime = uiState.trainStart,
                    onSelectedChanges = { time ->
                        trainViewModel.setTrainStart(time)
                    }
                )
            }
            HorizontalDivider(thickness = 2.dp)
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 10.dp,
                    bottom = 10.dp
                ),
                text = stringResource(R.string.train_end_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            Row (
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 25.dp
                ).background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.large,
                ).fillMaxWidth(),
            ) {
                TimeScrollWheel (
                    hours = (0..23).map { "%02d".format(it) },
                    minutes = (0..59).map { "%02d".format(it) },
                    seconds = (0..59).map { "%02d".format(it) },
                    initialTime = uiState.trainEnd,
                    onSelectedChanges = { time ->
                        trainViewModel.setTrainEnd(time)
                    }
                )
            }
        }
        Text(
            modifier = Modifier.padding(
                top = 25.dp,
                bottom = 10.dp
            ),
            text = stringResource(R.string.train_content_text),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            modifier = Modifier.padding(
                bottom = 10.dp
            ),
            text = stringResource(R.string.warm_up_text),
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
        )
        TrainPart(
            heartRateBefore = uiState.heartRateBeforeWarmUp,
            heartRateAfter = uiState.heartRateAfterWarmUp,
            duration = uiState.warmUpDuration,
            note = uiState.warmUpNote,
            onHeartRateBeforeChanges = { value ->
                trainViewModel.setHeartRateBeforeWarmUp(value)
            },
            onHeartRateAfterChanges = { value ->
                trainViewModel.setHeartRateAfterWarmUp(value)
            },
            onDurationChanges = { value ->
                trainViewModel.setWarmUpDuration(value)
            },
            onNoteChanges = { value ->
                trainViewModel.setWarmUpNote(value)
            },
        )
        Text(
            modifier = Modifier.padding(
                top = 25.dp,
                bottom = 10.dp
            ),
            text = stringResource(R.string.main_text),
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
        )
        TrainPart(
            heartRateBefore = uiState.heartRateBeforeMain,
            heartRateAfter = uiState.heartRateAfterMain,
            duration = uiState.mainDuration,
            note = uiState.mainNote,
            onHeartRateBeforeChanges = { value ->
                trainViewModel.setHeartRateBeforeMain(value)
            },
            onHeartRateAfterChanges = { value ->
                trainViewModel.setHeartRateAfterMain(value)
            },
            onDurationChanges = { value ->
                trainViewModel.setMainDuration(value)
            },
            onNoteChanges = { value ->
                trainViewModel.setMainNote(value)
            },
        )
        Text(
            modifier = Modifier.padding(
                top = 25.dp,
                bottom = 10.dp
            ),
            text = stringResource(R.string.finish_text),
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
        )
        TrainPart(
            heartRateBefore = uiState.heartRateBeforeFinish,
            heartRateAfter = uiState.heartRateAfterFinish,
            duration = uiState.finishDuration,
            note = uiState.finishNote,
            onHeartRateBeforeChanges = { value ->
                trainViewModel.setHeartRateBeforeFinish(value)
            },
            onHeartRateAfterChanges = { value ->
                trainViewModel.setHeartRateAfterFinish(value)
            },
            onDurationChanges = { value ->
                trainViewModel.setFinishDuration(value)
            },
            onNoteChanges = { value ->
                trainViewModel.setFinishNote(value)
            },
        )
    }
}

@Composable
fun TrainPart(
    heartRateBefore: Int,
    heartRateAfter: Int,
    duration: LocalTime,
    note: String,
    onHeartRateBeforeChanges: (index: Int) -> Unit,
    onHeartRateAfterChanges: (index: Int) -> Unit,
    onDurationChanges: (time: LocalTime) -> Unit,
    onNoteChanges: (value: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth().padding(top = 20.dp),
        shape = MaterialTheme.shapes.large,
    ) {
        Text(
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                top = 25.dp,
                bottom = 10.dp
            ),
            text = stringResource(R.string.heart_rate_before_text),
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
        )
        RowScrollWheel(
            items = (10..200).map { "$it" },
            initialState = heartRateBefore,
            onSelectedChanges = { index ->
                onHeartRateBeforeChanges(index)
            }
        )
        HorizontalDivider(thickness = 2.dp)
        Text(
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                top = 25.dp,
                bottom = 10.dp
            ),
            text = stringResource(R.string.heart_rate_after_text),
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
        )
        RowScrollWheel(
            items = (10..200).map { "$it" },
            initialState = heartRateAfter,
            onSelectedChanges = { index ->
                onHeartRateAfterChanges(index)
            }
        )
        HorizontalDivider(thickness = 2.dp)
        Text(
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                top = 10.dp,
                bottom = 10.dp
            ),
            text = stringResource(R.string.train_duration_text),
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
        )
        Row (
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                bottom = 10.dp
            ).background(
                color = Color.White,
                shape = MaterialTheme.shapes.large,
            ).fillMaxWidth(),
        ) {
            TimeScrollWheel (
                hours = (0..23).map { "%02d".format(it) },
                minutes = (0..59).map { "%02d".format(it) },
                seconds = (0..59).map { "%02d".format(it) },
                initialTime = duration,
                onSelectedChanges = { time ->
                    onDurationChanges(time)
                }
            )
        }
        HorizontalDivider(thickness = 2.dp)
        StyledCardTextField(
            value = note,
            label = R.string.day_note,
            onValueChange = {
                onNoteChanges(it)
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.empty_text),
                    modifier = Modifier.alpha(0.5f)
                )
            },
            maxLines = 5,
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                top = 15.dp,
                bottom = 25.dp,
            ),
        )
    }
}