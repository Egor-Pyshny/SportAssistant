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
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.domain.model.Coach
import com.example.sportassistant.presentation.aboutapp.ui.AboutAppScreen
import com.example.sportassistant.presentation.applayout.ui.LayoutSettingsScreen
import com.example.sportassistant.presentation.applayout.viewmodel.AppLayoutViewModel
import com.example.sportassistant.presentation.home.ui.HomeScreen
import com.example.sportassistant.presentation.homemain.ui.HomeMainScreen
import com.example.sportassistant.presentation.login.ui.LogInScreen
import com.example.sportassistant.presentation.pinned.ui.PinnedScreen
import com.example.sportassistant.presentation.premium.ui.PremiumScreen
import com.example.sportassistant.presentation.profile.ui.ProfileScreen
import com.example.sportassistant.presentation.profile.viewmodel.ProfileInfoViewModel
import com.example.sportassistant.presentation.profile.viewmodel.ProfileViewModel
import com.example.sportassistant.presentation.registration.ui.RegistrationCoachScreen
import com.example.sportassistant.presentation.registration.ui.RegistrationCreateAccountScreen
import com.example.sportassistant.presentation.registration.ui.RegistrationProfileScreen
import com.example.sportassistant.presentation.registration.viewmodel.RegistrationViewModel
import com.example.sportassistant.presentation.settings.ui.SettingsScreen
import com.example.sportassistant.presentation.start.ui.StartScreen
import com.example.sportassistant.presentation.theme.SportAssistantTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.KoinContext

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
    data object Calendar : Route("calendar")
    data object Pinned : Route("pinned")
    data object AboutApp : Route("settings_about_app")
    data object LayoutSettings : Route("settings_layout_settings")
    data object Premium : Route("settings_premium")
}

@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController(),
    registrationViewModel: RegistrationViewModel = koinViewModel(),
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
                    logout = {
                        coroutineScope.launch {
                            preferences.setIsLoggedIn(false)
                            preferences.saveSID("")
                        }
                        navController.navigate(GraphRoutes.AuthNav.route) { popUpTo(0) }
                    }
                )
            }
        }
    }
}

@Composable
fun HomeNavGraph(
    themeViewModel: AppLayoutViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    logout: () -> Unit,
) {
    val profileInfoViewModel: ProfileInfoViewModel = koinViewModel()
    val profileViewModel: ProfileViewModel = viewModel()
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
                navController = navController,
                viewModel = profileViewModel,
                infoViewModel = profileInfoViewModel,
                logout = logout,
            )
        }
        composable(route = HomeRoutes.Calendar.route) {
            Scaffold { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text="Calendar")
                }
            }
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
