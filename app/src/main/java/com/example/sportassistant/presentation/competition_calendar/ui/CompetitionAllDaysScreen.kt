package com.example.sportassistant.presentation.competition_calendar.ui

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
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.CompetitionDay
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.TabsViewModel
import com.example.sportassistant.presentation.components.StyledButtonList
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import org.koin.androidx.compose.get
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import java.util.UUID

@SuppressLint("NewApi")
@Composable
fun CompetitionAllDaysScreen(
    navController: NavHostController,
    competitionViewModel: CompetitionViewModel,
    titleViewModel: TitleViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by competitionViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        titleViewModel.setTitle("${uiState.selectedCompetition?.startDate} - ${uiState.selectedCompetition?.endDate}")
    }
    val numberOfDays = ChronoUnit.DAYS.between(uiState.selectedCompetition?.startDate, uiState.selectedCompetition?.endDate)
    val data = (0..numberOfDays).map { uiState.selectedCompetition!!.startDate.plusDays(it) }
    val formatter = DateTimeFormatter.ofPattern("EEEE - dd.MM.yyyy", Locale("ru"))
    val items = data
        .map { date ->
            date?.format(formatter)?.replaceFirstChar { it.uppercase() } ?: ""
        }
        .toList()
    StyledButtonList(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
                top = 20.dp,
                bottom = 20.dp
            ).verticalScroll(rememberScrollState()),
        items = items,
        onClick = { index, title ->
            titleViewModel.setTitle(title)
            competitionViewModel.setSelectedDay(data[index])
            navController.navigate(HomeRoutes.CompetitionDay.route)
        }
    )
}
