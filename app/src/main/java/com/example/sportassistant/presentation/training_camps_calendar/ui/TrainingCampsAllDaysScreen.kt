package com.example.sportassistant.presentation.training_camps_calendar.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.components.StyledButtonList
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import org.koin.androidx.compose.get
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@SuppressLint("NewApi")
@Composable
fun TrainingCampsAllDaysScreen(
    navController: NavHostController,
    titleViewModel: TitleViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val state = ApplicationState.getState()
    val formatter = DateTimeFormatter.ofPattern("EEEE - dd.MM.yyyy", Locale("ru"))

    LaunchedEffect(Unit) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("ru"))
        val startDate = state.camp?.startDate?.format(dateFormatter)
        val endDate = state.camp?.endDate?.format(dateFormatter)
        titleViewModel.setTitle("$startDate - $endDate")
    }
    val numberOfDays = ChronoUnit.DAYS.between(state.camp?.startDate, state.camp?.endDate)
    val data = (0..numberOfDays).map { state.camp!!.startDate.plusDays(it) }
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
            ApplicationState.setCampDay(data[index])
            navController.navigate(HomeRoutes.TrainingCampsDay.route)
        }
    )
}