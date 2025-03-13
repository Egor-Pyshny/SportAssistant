package com.example.sportassistant.presentation.homemain.ui

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.TabsViewModel
import com.example.sportassistant.presentation.components.TopTabsNavigation
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel

@Composable
fun CompetitionCalendarScreen(
    competitionViewModel: CompetitionViewModel,
    titleViewModel: TitleViewModel,
    tabsViewModel: TabsViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Scaffold (
        topBar = {
            TopTabsNavigation(
                tabsViewModel = tabsViewModel,
                updateData = { index ->
                    competitionViewModel.getCompetitions(index)
                },
            )
        },
        modifier = modifier.fillMaxSize()
    ) { padding ->
        CompetitionCalendarMainScreen(
            navController = navController,
            competitionViewModel = competitionViewModel,
            titleViewModel = titleViewModel,
            tabsViewModel = tabsViewModel,
            modifier = Modifier.padding(
                start = padding.calculateStartPadding(LayoutDirection.Ltr),
                top = padding.calculateTopPadding(),
                end = padding.calculateEndPadding(LayoutDirection.Ltr),
                bottom = 0.dp,
            ),
            onClick = {
                navController.navigate(HomeRoutes.CompetitionAllDaysNav.route)
            },
            onAddClick = {
                navController.navigate(HomeRoutes.CompetitionAdd.route)
            },
        )
    }
}
