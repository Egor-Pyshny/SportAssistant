package com.example.sportassistant.presentation.training_camps_calendar.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.components.StyledButtonList
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import com.example.sportassistant.presentation.training_camps_calendar.viewmodel.TrainingCampsViewModel
import org.koin.androidx.compose.get
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@SuppressLint("NewApi")
@Composable
fun TrainingCampsAllDaysScreen(
    navController: NavHostController,
    trainingCampsViewModel: TrainingCampsViewModel,
    titleViewModel: TitleViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by trainingCampsViewModel.uiState.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("EEEE - dd.MM.yyyy", Locale("ru"))

    LaunchedEffect(Unit) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("ru"))
        val startDate = uiState.selectedCamp?.startDate?.format(dateFormatter)
        val endDate = uiState.selectedCamp?.endDate?.format(dateFormatter)
        titleViewModel.setTitle("$startDate - $endDate")
    }
    val numberOfDays = ChronoUnit.DAYS.between(uiState.selectedCamp?.startDate, uiState.selectedCamp?.endDate)
    val data = (0..numberOfDays).map { uiState.selectedCamp!!.startDate.plusDays(it) }
    val items = data
        .map { date ->
            date?.format(formatter)?.replaceFirstChar { it.uppercase() } ?: ""
        }
        .toList().toMutableList()
    StyledButtonList(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
                bottom = 20.dp
            ).verticalScroll(rememberScrollState()),
        items = items,
        onClick = { index, title ->
            titleViewModel.setTitle(title)
            trainingCampsViewModel.setSelectedDay(data[index])
            trainingCampsViewModel.getCampsDay(
                campId = uiState.selectedCamp!!.id,
                day = data[index]
            )
            navController.navigate(HomeRoutes.TrainingCampsDay.route)
        }
    )
}