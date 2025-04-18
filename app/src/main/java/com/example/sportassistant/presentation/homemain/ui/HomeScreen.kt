package com.example.sportassistant.presentation.homemain.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sportassistant.R
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.GraphRoutes
import com.example.sportassistant.presentation.HomeNavGraph
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.Route
import com.example.sportassistant.presentation.applayout.viewmodel.AppLayoutViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import org.koin.androidx.compose.get

@Composable
fun HomeScreen(
    themeViewModel: AppLayoutViewModel,
    titleViewModel: TitleViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    logout: () -> Unit,
) {
    BackHandler {  }
    Scaffold (
        bottomBar = {
            GetBottomBar(
                navController = navController,
                onTabPressed = { it: Route ->
                    navController.navigate(it.route)
                }
            )
        },
        topBar = {
            GetTopBar(
                navController = navController,
                titleViewModel = titleViewModel,
                logout = logout,
            )
        },
        modifier = modifier
    ) { padding ->
        HomeNavGraph(
            themeViewModel = themeViewModel,
            titleViewModel = titleViewModel,
            navController = navController,
            logout = logout,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun GetBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    screenSizeProvider: WindowSizeProvider = get(),
    onTabPressed: ((Route) -> Unit),
) {
    val screensWithoutBottomBar = listOf<String>()
    val navigationItemContentList = listOf(
        NavigationItemContent(
            icon = R.drawable.profile,
            text = stringResource(id = R.string.nav_bar_profile_text),
            route = HomeRoutes.Profile,
        ),
        NavigationItemContent(
            icon = R.drawable.settings,
            text = stringResource(id = R.string.nav_bar_settings_text),
            route = HomeRoutes.Settings,
        ),
        NavigationItemContent(
            icon = R.drawable.calendar,
            text = stringResource(id = R.string.nav_bar_calendar_text),
            route = HomeRoutes.Calendar,
        ),
        NavigationItemContent(
            icon = R.drawable.pin,
            text = stringResource(id = R.string.nav_bar_pinned_text),
            route = HomeRoutes.Pinned,
        )
    )
    val size = screenSizeProvider.getScreenDimensions()
    val navBarFontSize = if (size.screenWidth <= 360.dp) {
        10.sp
    } else {
        12.sp
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (!screensWithoutBottomBar.contains(currentDestination?.route)) {
        NavigationBar(
            modifier = modifier,
            containerColor = Color.Transparent
        ) {
            for (navItem in navigationItemContentList) {
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { parentRoute ->
                        parentRoute.route?.startsWith(navItem.route.route) == true
                    } == true,
                    onClick = { onTabPressed(navItem.route) },
                    icon = {
                        Icon(
                            painter = painterResource(navItem.icon),
                            contentDescription = navItem.text
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent,
                    ),
                    label = {
                        Text(
                            text = navItem.text,
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = navBarFontSize)
                        )
                    },
                )
            }
        }
    }
}

private data class NavigationItemContent(
    val icon: Int,
    val text: String,
    val route: Route,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GetTopBar(
    navController: NavHostController,
    titleViewModel: TitleViewModel,
    modifier: Modifier = Modifier,
    logout: () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screensWithoutTopBar = listOf(
        HomeRoutes.Home.route,
    )
    val screensWithReturnToHomeIcon = listOf(
        HomeRoutes.Pinned.route,
        HomeRoutes.Profile.route,
        HomeRoutes.Settings.route,
        HomeRoutes.Calendar.route
    )
    if (!screensWithoutTopBar.contains(currentDestination?.route)) {

        val currentScreen = getTopBarTitles(
            route = currentDestination?.route ?: "",
            titleViewModel = titleViewModel,
        )
        val iconText = getIconText(currentDestination?.route)
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = currentScreen,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            modifier = modifier.wrapContentHeight(),
            navigationIcon = {
                Row(
                    modifier = Modifier
                        .clickable {
                            if (currentDestination?.route in screensWithReturnToHomeIcon) {
                                navController.navigate(HomeRoutes.Home.route)
                            } else {
                                navController.popBackStack()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.chevron_left),
                        contentDescription = null
                    )
                    Text(
                        text = iconText,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 17.sp
                        ),
                        modifier = Modifier.padding(end=10.dp)
                    )
                }
            },
            actions = {
                if (currentDestination?.route?.startsWith(HomeRoutes.Profile.route) == true) {
                    IconButton(onClick = logout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Выход из аккаунта"
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun getIconText(route: String?): String {
    val text = when (route) {
        HomeRoutes.Pinned.route,
        HomeRoutes.Profile.route,
        HomeRoutes.Settings.route,
        HomeRoutes.Calendar.route -> stringResource(R.string.nav_bar_main_text)
        else -> stringResource(R.string.nav_bar_back_text)
    }
    return text
}

@Composable
private fun getTopBarTitles(
    route: String,
    titleViewModel: TitleViewModel,
): String {
    var title = when (route) {
        HomeRoutes.Pinned.route -> stringResource(R.string.nav_bar_pinned_text)
        HomeRoutes.Profile.route -> stringResource(R.string.nav_bar_profile_text)
        HomeRoutes.Settings.route -> stringResource(R.string.nav_bar_settings_text)
        HomeRoutes.Calendar.route -> stringResource(R.string.nav_bar_calendar_text)
        HomeRoutes.LayoutSettings.route -> stringResource(R.string.nav_bar_layout_settings_text)
        HomeRoutes.AboutApp.route -> stringResource(R.string.nav_bar_about_app_text)
        HomeRoutes.Premium.route -> stringResource(R.string.nav_bar_about_app_text)
        HomeRoutes.ProfileUser.route -> stringResource(R.string.nav_bar_profile_user)
        HomeRoutes.ProfileCoach.route -> stringResource(R.string.nav_bar_profile_coach)
        HomeRoutes.Competitions.route -> stringResource(R.string.home_list_item_competition_calendar)
        HomeRoutes.TrainingCamps.route -> stringResource(R.string.home_list_item_working_process)
        HomeRoutes.OFPResults.route -> stringResource(R.string.home_list_item_ofp_testing)
        HomeRoutes.SFPResults.route -> stringResource(R.string.home_list_item_cfp_testing)
        HomeRoutes.OFPResultsGraphic.route -> stringResource(R.string.home_list_item_ofp_testing)
        HomeRoutes.SFPResultsGraphic.route -> stringResource(R.string.home_list_item_cfp_testing)
        HomeRoutes.AnthropometricParams.route -> stringResource(R.string.home_list_item_ant_params)
        HomeRoutes.CompetitionAdd.route -> stringResource(R.string.add_competition)
        HomeRoutes.OFPResultsAdd.route -> stringResource(R.string.add_ofp)
        HomeRoutes.SFPResultsAdd.route -> stringResource(R.string.add_sfp)
        HomeRoutes.NotesAdd.route -> stringResource(R.string.add_note)
        HomeRoutes.Notes.route -> stringResource(R.string.home_list_item_note)
        HomeRoutes.MedExamination.route -> stringResource(R.string.home_list_item_med_exam)
        HomeRoutes.MedExaminationAdd.route -> stringResource(R.string.add_med_exam)
        HomeRoutes.ComprehensiveExamination.route -> stringResource(R.string.home_list_item_complex_exam)
        HomeRoutes.ComprehensiveExaminationAdd.route -> stringResource(R.string.add_comprehensive_exam)
        HomeRoutes.AnthropometricParamsAdd.route -> stringResource(R.string.add_ant_params)
        HomeRoutes.AnthropometricParamsGraphic.route -> stringResource(R.string.home_list_item_ant_params)
        HomeRoutes.Activity.route -> stringResource(R.string.diary_list_item_activity)
        HomeRoutes.Sleep.route -> stringResource(R.string.diary_list_item_sleep)
        HomeRoutes.TrainDiary.route -> stringResource(R.string.home_list_item_train_diary)
        HomeRoutes.Preparations.route -> stringResource(R.string.diary_list_item_preparations)
        HomeRoutes.Food.route -> stringResource(R.string.diary_list_item_food)
        HomeRoutes.Meal.route -> stringResource(R.string.diary_list_item_food)
        else -> "ERROR"
    }
    if (title == "ERROR") {
        val uiState by titleViewModel.uiState.collectAsState()
        title = uiState
    }
    println(route)
    return title
}
