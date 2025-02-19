package com.example.sportassistant.presentation.homemain.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.CompetitionNavGraph
import com.example.sportassistant.presentation.HomeNavGraph
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.Route
import com.example.sportassistant.presentation.applayout.viewmodel.AppLayoutViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionTitleViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.TabsViewModel
import com.example.sportassistant.presentation.components.TopTabsNavigation
import org.koin.androidx.compose.get

@Composable
fun CompetitionCalendarScreen(
    competitionTitleViewModel: CompetitionTitleViewModel,
    tabsViewModel: TabsViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    Scaffold (
        topBar = {
            TopTabsNavigation(
                tabsViewModel = tabsViewModel,
                navController = navController,
            )
        },
        modifier = modifier
    ) { padding ->
        CompetitionNavGraph(
            navController = navController,
            competitionTitleViewModel = competitionTitleViewModel,
            tabsViewModel = tabsViewModel,
            modifier = Modifier.padding(padding),
        )
    }
}
