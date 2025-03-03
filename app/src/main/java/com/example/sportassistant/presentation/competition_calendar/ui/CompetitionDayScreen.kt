package com.example.sportassistant.presentation.competition_calendar.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.components.StyledButtonList
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import org.koin.androidx.compose.get
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CompetitionDayScreen(
    competitionViewModel: CompetitionViewModel,
    titleViewModel: TitleViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val uiState by competitionViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        val formatter = DateTimeFormatter.ofPattern("EEEE - dd.MM.yyyy", Locale("ru"))
        val formatedDate = uiState.selectedDay!!.format(formatter).replaceFirstChar { it.uppercase() }
        titleViewModel.setTitle(formatedDate)
    }

}
