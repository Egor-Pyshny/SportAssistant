package com.example.sportassistant.presentation.competition_calendar.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.domain.application_state.ApplicationState
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
    titleViewModel: TitleViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val state = ApplicationState.getState()
    val selectedCompetition = state.competition
    val formatter = DateTimeFormatter.ofPattern("EEEE - dd.MM.yyyy", Locale("ru"))
    LaunchedEffect(Unit) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("ru"))
        val startDate = selectedCompetition?.startDate?.format(dateFormatter)
        val endDate = selectedCompetition?.endDate?.format(dateFormatter)
        titleViewModel.setTitle("$startDate - $endDate")
    }
    val numberOfDays = ChronoUnit.DAYS.between(selectedCompetition?.startDate, selectedCompetition?.endDate)
    val data = (0..numberOfDays).map { selectedCompetition!!.startDate.plusDays(it) }
    val items = data
        .map { date ->
            date?.format(formatter)?.replaceFirstChar { it.uppercase() } ?: ""
        }
        .toList().toMutableList()
    val resultTitle = stringResource(R.string.competition_result_title)
    items += resultTitle
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
            if (title == resultTitle) {
                navController.navigate(HomeRoutes.CompetitionResult.route)
            } else {
                ApplicationState.setCompetitionDay(data[index])
                navController.navigate(HomeRoutes.CompetitionDay.route)
            }
        }
    )
}
