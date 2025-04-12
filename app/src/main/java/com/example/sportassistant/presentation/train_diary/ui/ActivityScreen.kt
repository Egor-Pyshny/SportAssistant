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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.components.RowScrollWheel
import com.example.sportassistant.presentation.components.TimeScrollWheel
import com.example.sportassistant.presentation.train_diary.viewmodel.TrainDiaryViewModel
import org.koin.androidx.compose.get

@Composable
fun ActivityScreen(
    viewModel: TrainDiaryViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by viewModel.uiState.collectAsState()
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
                .fillMaxWidth().padding(top=20.dp),
            shape = MaterialTheme.shapes.large,
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 25.dp,
                    bottom = 10.dp
                ),
                text = stringResource(R.string.day_activity_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            RowScrollWheel(
                items = (1..10).map { "$it" },
                initialState = uiState.dayActivity,
                onSelectedChanges = { index ->
                    viewModel.setDayActivity(index)
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
                text = stringResource(R.string.evening_health_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            RowScrollWheel(
                items = (1..10).map { "$it" },
                initialState = uiState.eveningHealth,
                onSelectedChanges = { index ->
                    viewModel.setEveningHealth(index)
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
                text = stringResource(R.string.day_tired_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            RowScrollWheel(
                items = (1..10).map { "$it" },
                initialState = uiState.dayTired,
                onSelectedChanges = { index ->
                    viewModel.setDayTired(index)
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
                text = stringResource(R.string.going_to_bed_time),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
            )
            Row (
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 25.dp
                ).background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.large,
                ).fillMaxWidth(),
            ) {
                TimeScrollWheel (
                    hours = (0..23).map { "%02d".format(it) },
                    minutes = (0..59).map { "%02d".format(it) },
                    seconds = (0..59).map { "%02d".format(it) },
                    initialTime = uiState.goToBedTime,
                    onSelectedChanges = { time ->
                        viewModel.setGoToBedTime(time)
                    }
                )
            }
        }
    }
}
