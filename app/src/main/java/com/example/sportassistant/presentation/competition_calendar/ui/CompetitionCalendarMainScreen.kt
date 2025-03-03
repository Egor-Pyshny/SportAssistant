package com.example.sportassistant.presentation.homemain.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.TabsViewModel
import com.example.sportassistant.presentation.components.StyledButtonList
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import com.example.sportassistant.presentation.utils.ApiResponse
import org.koin.androidx.compose.get
import java.time.format.DateTimeFormatter


@Composable
fun CompetitionCalendarMainScreen(
    tabsViewModel: TabsViewModel,
    competitionViewModel: CompetitionViewModel,
    titleViewModel: TitleViewModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onAddClick: () -> Unit,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val tabIndex by tabsViewModel.uiState.collectAsState()
    val competitionsResponse by when (tabIndex) {
        0 -> {
            competitionViewModel.getCompetitionsPrevResponse.observeAsState()
        }
        1 -> {
            competitionViewModel.getCompetitionsCurrentResponse.observeAsState()
        }
        2 -> {
            competitionViewModel.getCompetitionsNextResponse.observeAsState()
        }
        else -> {
            throw Exception()
        }
    }
    LaunchedEffect(Unit) {
        competitionViewModel.getCompetitions(tabIndex)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (competitionsResponse) {
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
                val data = (competitionsResponse as ApiResponse.Success<List<Competition>?>).data
                    ?: listOf()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val strings = data.map {
                    "${it.startDate.format(formatter)} - ${it.endDate.format(formatter)}"
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    StyledButtonList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = screenSizeProvider.getEdgeSpacing(),
                                end = screenSizeProvider.getEdgeSpacing(),
                                top = 20.dp,
                                bottom = 20.dp
                            )
                            .verticalScroll(rememberScrollState()),
                        items = strings,
                        onClick = { index, title ->
                            competitionViewModel.setSelectedCompetition(data[index])
                            onClick()
                        }
                    )
                    FloatingActionButton(
                        onClick = onAddClick,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(30.dp),
                        shape = CircleShape,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.plus_icon),
                            null,
                        )
                    }
                }
            }
            is ApiResponse.Failure -> {
                Text(
                    text = (competitionsResponse as ApiResponse.Failure).errorMessage
                )
            }
            else -> {}
        }
    }
}
