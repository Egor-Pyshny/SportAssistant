package com.example.sportassistant.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sportassistant.presentation.login.ui.LogInScreen
import com.example.sportassistant.presentation.registration.ui.RegistrationCoachScreen
import com.example.sportassistant.presentation.registration.ui.RegistrationCreateAccountScreen
import com.example.sportassistant.presentation.registration.ui.RegistrationProfileScreen
import com.example.sportassistant.presentation.registration.viewmodel.RegistrationViewModel
import com.example.sportassistant.presentation.start.ui.StartScreen

enum class AppScreen {
    Start(),
    RegistrationStart(),
    RegistrationProfile(),
    RegistrationCoach(),
    LogIn(),
    Home(),
}

@Composable
fun SportAssistantApp(
    registrationViewModel: RegistrationViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Start.name,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        composable(route = AppScreen.Start.name) {
            StartScreen(
                onRegistrationButtonClicked = {
                    Log.d("Navigation", "to -> Registration")
                    navigateTo(AppScreen.RegistrationStart, navController)
                },
                onLogInButtonClicked = {
                    Log.d("Navigation", "to -> LogIn")
                    navigateTo(AppScreen.LogIn, navController)
                }
            )
        }
        composable(route = AppScreen.RegistrationStart.name) {
            RegistrationCreateAccountScreen(
                viewModel = registrationViewModel,
                onContinueRegistrationButtonClick = {
                    Log.d("Navigation", "to -> RegistrationProfile")
                    navigateTo(AppScreen.RegistrationProfile, navController)
                }
            )
        }
        composable(route = AppScreen.RegistrationCoach.name) {
            RegistrationCoachScreen(
                viewModel = registrationViewModel,
                onFinishRegistrationButtonClick = {
                    Log.d("Navigation", "to -> Home")
                    navigateTo(AppScreen.Home, navController)
                }
            )
        }
        composable(route = AppScreen.RegistrationProfile.name) {
            RegistrationProfileScreen(
                viewModel = registrationViewModel,
                onContinueRegistrationButtonClick = {
                    Log.d("Navigation", "to -> RegistrationCoach")
                    navigateTo(AppScreen.RegistrationCoach, navController)
                }
            )
        }
        composable(route = AppScreen.LogIn.name) {
            LogInScreen(
                onLogInButtonClick = {
                    Log.d("Navigation", "to -> Home")
                    navigateTo(AppScreen.Home, navController)
                }
            )
        }
        composable(route = AppScreen.Home.name) {

        }
    }
}

private fun navigateTo(
    appScreen: AppScreen,
    navController: NavHostController
) {
    navController.navigate(appScreen.name)
}