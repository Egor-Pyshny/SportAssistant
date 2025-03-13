package com.example.sportassistant.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.presentation.aboutapp.ui.AboutAppScreen
import com.example.sportassistant.presentation.ant_params.ui.AnthropometricParamsScreen
import com.example.sportassistant.presentation.ant_params.viewmodel.AnthropometricParamsViewModel
import com.example.sportassistant.presentation.ant_params_add.ui.AnthropometricParamsAddScreen
import com.example.sportassistant.presentation.ant_params_info.ui.AnthropometricParamsInfoScreen
import com.example.sportassistant.presentation.applayout.ui.LayoutSettingsScreen
import com.example.sportassistant.presentation.applayout.viewmodel.AppLayoutViewModel
import com.example.sportassistant.presentation.calendar.ui.CalendarScreen
import com.example.sportassistant.presentation.competition_add.ui.CompetitionAddScreen
import com.example.sportassistant.presentation.competition_calendar.ui.CompetitionAllDaysScreen
import com.example.sportassistant.presentation.competition_day.ui.CompetitionDayScreen
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.TabsViewModel
import com.example.sportassistant.presentation.competition_day.ui.CompetitionResultScreen
import com.example.sportassistant.presentation.competition_info.ui.CompetitionInfoScreen
import com.example.sportassistant.presentation.homemain.ui.CompetitionCalendarScreen
import com.example.sportassistant.presentation.homemain.ui.HomeScreen
import com.example.sportassistant.presentation.homemain.ui.HomeMainScreen
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import com.example.sportassistant.presentation.login.ui.LogInScreen
import com.example.sportassistant.presentation.ofp_result_add.ui.OFPResultAddScreen
import com.example.sportassistant.presentation.ofp_results.ui.OFPResultsScreen
import com.example.sportassistant.presentation.ofp_results.viewmodel.OFPResultsViewModel
import com.example.sportassistant.presentation.ofp_results_info.ui.OFPResultsInfoScreen
import com.example.sportassistant.presentation.pinned.ui.PinnedScreen
import com.example.sportassistant.presentation.premium.ui.PremiumScreen
import com.example.sportassistant.presentation.profile.ui.CoachInfoScreen
import com.example.sportassistant.presentation.profile.ui.ProfileScreen
import com.example.sportassistant.presentation.profile.ui.UserInfoScreen
import com.example.sportassistant.presentation.profile.viewmodel.ProfileInfoViewModel
import com.example.sportassistant.presentation.profile.viewmodel.ProfileViewModel
import com.example.sportassistant.presentation.registration.ui.RegistrationCoachScreen
import com.example.sportassistant.presentation.registration.ui.RegistrationCreateAccountScreen
import com.example.sportassistant.presentation.registration.ui.RegistrationProfileScreen
import com.example.sportassistant.presentation.registration.viewmodel.RegistrationViewModel
import com.example.sportassistant.presentation.settings.ui.SettingsScreen
import com.example.sportassistant.presentation.sfp_result_add.ui.SFPResultAddScreen
import com.example.sportassistant.presentation.sfp_results.ui.SFPResultsScreen
import com.example.sportassistant.presentation.sfp_results.viewmodel.SFPResultsViewModel
import com.example.sportassistant.presentation.sfp_results_info.ui.SFPResultsInfoScreen
import com.example.sportassistant.presentation.start.ui.StartScreen
import com.example.sportassistant.presentation.theme.SportAssistantTheme
import com.example.sportassistant.presentation.trainig_camps_add.ui.TrainingCampAddScreen
import com.example.sportassistant.presentation.training_camp_info_info.ui.TrainingCampInfoScreen
import com.example.sportassistant.presentation.training_camps_calendar.ui.TrainingCampsAllDaysScreen
import com.example.sportassistant.presentation.training_camps_calendar.ui.TrainingCampsCalendarScreen
import com.example.sportassistant.presentation.training_camps_calendar.viewmodel.TrainingCampsViewModel
import com.example.sportassistant.presentation.training_camps_day.ui.TrainingCampDayScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

open class Route(val route: String)

sealed class AuthRoutes {
    data object Start : Route("start")
    data object RegistrationStart : Route("registration_start")
    data object RegistrationProfile : Route("registration_profile")
    data object RegistrationCoach : Route("registration_coach")
    data object LogIn : Route("login")
}

sealed class GraphRoutes {
    data object AuthNav : Route("AUTH_GRAPH")
    data object HomeNav : Route("HOME_GRAPH")
}

sealed class HomeRoutes {
    data object Home : Route("home")
    data object Settings : Route("settings")
    data object Profile : Route("profile")
    data object ProfileUser : Route("profile_user")
    data object ProfileCoach : Route("profile_coach")
    data object Calendar : Route("calendar")
    data object Pinned : Route("pinned")
    data object AboutApp : Route("settings_about_app")
    data object LayoutSettings : Route("settings_layout_settings")
    data object Premium : Route("settings_premium")
    data object CompetitionAllDaysNav : Route("all_competition_days")
    data object CompetitionDay : Route("competition_day")
    data object CompetitionResult : Route("competition_result")
    data object CompetitionAdd : Route("competition_add")
    data object Competitions : Route("all_competitions")
    data object CompetitionInfo : Route("competition_info")
    data object TrainingCampsAllDaysNav : Route("all_camps_days")
    data object TrainingCampsDay : Route("camp_day")
    data object TrainingCampsAdd : Route("camp_add")
    data object TrainingCamps : Route("all_camps")
    data object TrainingCampsInfo : Route("camp_info")
    data object OFPResults : Route("ofp_results")
    data object OFPResultsAdd : Route("ofp_results_add")
    data object OFPResultsInfo : Route("ofp_results_info")
    data object SFPResults : Route("sfp_results")
    data object SFPResultsAdd : Route("sfp_results_add")
    data object SFPResultsInfo : Route("sfp_results_info")
    data object AnthropometricParams : Route("anthropometric_params_results")
    data object AnthropometricParamsAdd : Route("anthropometric_params_add")
    data object AnthropometricParamsInfo : Route("anthropometric_params_info")
}

@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController(),
    registrationViewModel: RegistrationViewModel = koinViewModel(),
    titleViewModel: TitleViewModel = viewModel(),
) {
    val preferences: UserPreferencesRepository = get()
    var startDestination = GraphRoutes.AuthNav.route
    val isUserLoggedIn by preferences.isLoggedIn().collectAsState(initial = false)
    if (isUserLoggedIn) {
        startDestination = GraphRoutes.HomeNav.route
    }
    val coroutineScope = rememberCoroutineScope()
    val themeViewModel: AppLayoutViewModel = koinViewModel()
    themeViewModel.loadTheme()
    val tabsViewModel: TabsViewModel = viewModel()

    SportAssistantTheme(
        viewModel = themeViewModel
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            authNavGraph(
                navController = navController,
                registrationViewModel = registrationViewModel,
            )
            composable(route = GraphRoutes.HomeNav.route) {
                HomeScreen(
                    themeViewModel = themeViewModel,
                    titleViewModel = titleViewModel,
                    logout = {
                        coroutineScope.launch {
                            preferences.setIsLoggedIn(false)
                            preferences.saveSID("")
                        }
                        navController.navigate(GraphRoutes.AuthNav.route) { popUpTo(0) }
                    },
                )
            }
        }
    }
}

@Composable
fun HomeNavGraph(
    themeViewModel: AppLayoutViewModel,
    titleViewModel: TitleViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    competitionViewModel: CompetitionViewModel = koinViewModel(),
    trainingCampsViewModel: TrainingCampsViewModel = koinViewModel(),
    ofpResultsViewModel: OFPResultsViewModel = koinViewModel(),
    sfpResultsViewModel: SFPResultsViewModel = koinViewModel(),
    anthropometricParamsViewModel: AnthropometricParamsViewModel = koinViewModel(),
    logout: () -> Unit,
) {
    val profileInfoViewModel: ProfileInfoViewModel = koinViewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val competitionTabsViewModel: TabsViewModel = viewModel()
    val trainingCampsTabsViewModel: TabsViewModel = viewModel()
    NavHost(
        navController = navController,
        route = GraphRoutes.HomeNav.route,
        startDestination = HomeRoutes.Home.route,
        modifier = modifier,
    ) {
        composable(route = HomeRoutes.Home.route) {
            HomeMainScreen(navController)
        }
        composable(route = HomeRoutes.Settings.route) {
            SettingsScreen(navController)
        }
        composable(route = HomeRoutes.LayoutSettings.route) {
            LayoutSettingsScreen(viewModel = themeViewModel)
        }
        composable(route = HomeRoutes.AboutApp.route) {
            AboutAppScreen()
        }
        composable(route = HomeRoutes.Premium.route) {
            PremiumScreen()
        }
        composable(route = HomeRoutes.Pinned.route) {
            PinnedScreen()
        }
        composable(route = HomeRoutes.Profile.route) {
            if (profileInfoViewModel.shouldRefetch.collectAsState().value) {
                LaunchedEffect(Unit) {
                    profileInfoViewModel.fetchData()
                }
            }
            ProfileScreen(
                infoViewModel = profileInfoViewModel,
                logout = logout,
                onUserClick = {
                    navigateTo(HomeRoutes.ProfileUser, navController)
                },
                onCoachClick = {
                    navigateTo(HomeRoutes.ProfileCoach, navController)
                }
            )
        }
        composable(route = HomeRoutes.ProfileUser.route) {
            UserInfoScreen(
                infoViewModel = profileInfoViewModel
            )
        }
        composable(route = HomeRoutes.ProfileCoach.route) {
            CoachInfoScreen(
                infoViewModel = profileInfoViewModel
            )
        }
        composable(route = HomeRoutes.Competitions.route) {
            CompetitionCalendarScreen(
                competitionViewModel = competitionViewModel,
                tabsViewModel = competitionTabsViewModel,
                titleViewModel = titleViewModel,
                navController = navController,
            )
        }
        composable(route = HomeRoutes.TrainingCamps.route) {
            TrainingCampsCalendarScreen(
                trainingCampsViewModel = trainingCampsViewModel,
                tabsViewModel = trainingCampsTabsViewModel,
                titleViewModel = titleViewModel,
                navController = navController,
            )
        }
        composable(route = HomeRoutes.CompetitionAllDaysNav.route){
            CompetitionAllDaysScreen(
                navController = navController,
                competitionViewModel = competitionViewModel,
                titleViewModel = titleViewModel,
            )
        }
        composable(route = HomeRoutes.TrainingCampsAllDaysNav.route){
            TrainingCampsAllDaysScreen(
                navController = navController,
                trainingCampsViewModel = trainingCampsViewModel,
                titleViewModel = titleViewModel,
            )
        }
        composable(route = HomeRoutes.CompetitionDay.route){
            CompetitionDayScreen(
                competitionViewModel = competitionViewModel,
            )
        }
        composable(route = HomeRoutes.TrainingCampsDay.route){
            TrainingCampDayScreen(
                trainingCampsViewModel = trainingCampsViewModel,
            )
        }
        composable(route = HomeRoutes.CompetitionResult.route){
            CompetitionResultScreen(
                competitionViewModel = competitionViewModel,
            )
        }
        composable(route = HomeRoutes.CompetitionAdd.route){
            CompetitionAddScreen(
                navController = navController,
                titleViewModel = titleViewModel,
                competitionViewModel = competitionViewModel,
            )
        }
        composable(route = HomeRoutes.TrainingCampsAdd.route){
            TrainingCampAddScreen(
                navController = navController,
                titleViewModel = titleViewModel,
                trainingCampsViewModel = trainingCampsViewModel,
            )
        }
        composable(route = HomeRoutes.CompetitionInfo.route) {
            CompetitionInfoScreen(
                competitionViewModel = competitionViewModel,
            )
        }
        composable(route = HomeRoutes.TrainingCampsInfo.route) {
            TrainingCampInfoScreen(
                trainingCampViewModel = trainingCampsViewModel,
            )
        }
        composable(route = HomeRoutes.Calendar.route) {
            CalendarScreen()
        }
        composable(route = HomeRoutes.OFPResults.route) {
            OFPResultsScreen(
                navController = navController,
                titleViewModel = titleViewModel,
                ofpResultsViewModel = ofpResultsViewModel,
            )
        }
        composable(route = HomeRoutes.OFPResultsAdd.route) {
            OFPResultAddScreen(
                navController = navController,
                titleViewModel = titleViewModel,
                ofpResultsViewModel = ofpResultsViewModel,
            )
        }
        composable(route = HomeRoutes.OFPResultsInfo.route) {
            OFPResultsInfoScreen(
                ofpResultsViewModel = ofpResultsViewModel,
            )
        }
        composable(route = HomeRoutes.SFPResults.route) {
            SFPResultsScreen(
                navController = navController,
                titleViewModel = titleViewModel,
                sfpResultsViewModel = sfpResultsViewModel,
            )
        }
        composable(route = HomeRoutes.SFPResultsAdd.route) {
            SFPResultAddScreen(
                navController = navController,
                titleViewModel = titleViewModel,
                sfpResultsViewModel = sfpResultsViewModel,
            )
        }
        composable(route = HomeRoutes.SFPResultsInfo.route) {
            SFPResultsInfoScreen(
                sfpResultsViewModel = sfpResultsViewModel,
            )
        }
        composable(route = HomeRoutes.AnthropometricParams.route) {
            AnthropometricParamsScreen(
                navController = navController,
                titleViewModel = titleViewModel,
                anthropometricParamsViewModel = anthropometricParamsViewModel,
            )
        }
        composable(route = HomeRoutes.AnthropometricParamsAdd.route) {
            AnthropometricParamsAddScreen(
                navController = navController,
                titleViewModel = titleViewModel,
                anthropometricParamsViewModel = anthropometricParamsViewModel,
            )
        }
        composable(route = HomeRoutes.AnthropometricParamsInfo.route) {
            AnthropometricParamsInfoScreen(
                anthropometricParamsViewModel = anthropometricParamsViewModel,
            )
        }
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    registrationViewModel: RegistrationViewModel,
) {
    navigation(
        route = GraphRoutes.AuthNav.route,
        startDestination = AuthRoutes.Start.route,
    ) {
        composable(route = AuthRoutes.Start.route){
            StartScreen(
                onRegistrationButtonClicked = {
                    Log.d("Navigation", "to -> Registration")
                    navigateTo(AuthRoutes.RegistrationStart, navController)
                },
                onLogInButtonClicked = {
                    Log.d("Navigation", "to -> LogIn")
                    navigateTo(AuthRoutes.LogIn, navController)
                }
            )
        }
        composable(route = AuthRoutes.LogIn.route){
            LogInScreen(
                onLogInButtonClick = {
                    Log.d("Navigation", "to -> Home")
                    navigateTo(GraphRoutes.HomeNav, navController)
                }
            )
        }
        composable(route = AuthRoutes.RegistrationStart.route){
            RegistrationCreateAccountScreen(
                viewModel = registrationViewModel,
                onContinueRegistrationButtonClick = {
                    Log.d("Navigation", "to -> RegistrationProfile")
                    navigateTo(AuthRoutes.RegistrationProfile, navController)
                }
            )
        }
        composable(route = AuthRoutes.RegistrationCoach.route){
            RegistrationCoachScreen(
                viewModel = registrationViewModel,
                onFinishRegistrationButtonClick = {
                    Log.d("Navigation", "to -> Home")
                    navigateTo(GraphRoutes.HomeNav, navController)
                }
            )
        }
        composable(route = AuthRoutes.RegistrationProfile.route){
            RegistrationProfileScreen(
                viewModel = registrationViewModel,
                onContinueRegistrationButtonClick = {
                    Log.d("Navigation", "to -> RegistrationCoach")
                    navigateTo(AuthRoutes.RegistrationCoach, navController)
                }
            )
        }
    }
}


private fun navigateTo(
    appScreen: Route,
    navController: NavHostController
) {
    navController.navigate(appScreen.route)
}
