package com.example.sportassistant.presentation.homemain.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.Route
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionTitleViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.TabsViewModel
import com.example.sportassistant.presentation.components.Border
import com.example.sportassistant.presentation.components.StyledButtonListItem
import org.koin.androidx.compose.get

@Composable
fun CompetitionCalendarMainScreen(
    navController: NavHostController,
    tabsViewModel: TabsViewModel,
    competitionTitleViewModel: CompetitionTitleViewModel,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
) {
    val tabIndex by tabsViewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = screenSizeProvider.getEdgeSpacing(),
                end = screenSizeProvider.getEdgeSpacing(),
            ).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }
}
