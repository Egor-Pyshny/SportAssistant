package com.example.sportassistant.presentation.training_camps_calendar.ui

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
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.competition_calendar.viewmodel.TabsViewModel
import com.example.sportassistant.presentation.components.TopTabsNavigation
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import com.example.sportassistant.presentation.training_camps_calendar.viewmodel.TrainingCampsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrainingCampsCalendarScreen(
    tabsViewModel: TabsViewModel,
    navController: NavHostController,
    trainingCampsViewModel: TrainingCampsViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    Scaffold (
        topBar = {
            TopTabsNavigation(
                tabsViewModel = tabsViewModel,
                updateData = { index ->
                    trainingCampsViewModel.getCamps(index)
                },
            )
        },
        modifier = modifier.fillMaxSize()
    ) { padding ->
        TrainingCampsCalendarMainScreen(
            navController = navController,
            trainingCampsViewModel = trainingCampsViewModel,
            tabsViewModel = tabsViewModel,
            modifier = Modifier.padding(
                start = padding.calculateStartPadding(LayoutDirection.Ltr),
                top = padding.calculateTopPadding(),
                end = padding.calculateEndPadding(LayoutDirection.Ltr),
                bottom = 0.dp,
            ),
            onClick = {
                navController.navigate(HomeRoutes.TrainingCampsAllDaysNav.route)
            },
            onAddClick = {
                navController.navigate(HomeRoutes.TrainingCampsAdd.route)
            },
        )
    }
}
