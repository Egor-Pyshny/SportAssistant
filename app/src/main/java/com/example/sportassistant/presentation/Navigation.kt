package com.example.sportassistant.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.sportassistant.presentation.ant_params_add.ui.AnthropometricParamsAddScreen
import com.example.sportassistant.presentation.ant_params_graphic.ui.AnthropometricParamsGraphicScreen
import com.example.sportassistant.presentation.ant_params_info.ui.AnthropometricParamsInfoScreen
import com.example.sportassistant.presentation.applayout.ui.LayoutSettingsScreen
import com.example.sportassistant.presentation.applayout.viewmodel.AppLayoutViewModel
import com.example.sportassistant.presentation.calendar.ui.CalendarScreen
import com.example.sportassistant.presentation.competition_add.ui.CompetitionAddScreen
import com.example.sportassistant.presentation.competition_calendar.ui.CompetitionAllDaysScreen
import com.example.sportassistant.presentation.competition_day.ui.CompetitionDayScreen
import com.example.sportassistant.presentation.competition_calendar.viewmodel.TabsViewModel
import com.example.sportassistant.presentation.competition_result.ui.CompetitionResultScreen
import com.example.sportassistant.presentation.competition_info.ui.CompetitionInfoScreen
import com.example.sportassistant.presentation.comprehensive_examination.ui.ComprehensiveExaminationScreen
import com.example.sportassistant.presentation.comprehensive_examination_add.ui.ComprehensiveExaminationAddScreen
import com.example.sportassistant.presentation.comprehensive_examination_info.ui.ComprehensiveExaminationInfoScreen
import com.example.sportassistant.presentation.competition_calendar.ui.CompetitionCalendarScreen
import com.example.sportassistant.presentation.components.Loader
import com.example.sportassistant.presentation.homemain.ui.HomeScreen
import com.example.sportassistant.presentation.homemain.ui.HomeMainScreen
import com.example.sportassistant.presentation.homemain.viewmodel.TitleViewModel
import com.example.sportassistant.presentation.login.ui.LogInScreen
import com.example.sportassistant.presentation.med_examination.ui.MedExaminationScreen
import com.example.sportassistant.presentation.med_examination_add.ui.MedExaminationAddScreen
import com.example.sportassistant.presentation.med_examination_info.ui.MedExaminationInfoScreen
import com.example.sportassistant.presentation.note_add.ui.NoteAddScreen
import com.example.sportassistant.presentation.note_info.ui.NoteInfoScreen
import com.example.sportassistant.presentation.notes.ui.NotesScreen
import com.example.sportassistant.presentation.ofp_graphic.ui.OFPResultsGraphicScreen
import com.example.sportassistant.presentation.ofp_result_add.ui.OFPResultAddScreen
import com.example.sportassistant.presentation.ofp_results.ui.OFPResultsScreen
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
import com.example.sportassistant.presentation.registration.viewmodel.SetProfileInfoViewModel
import com.example.sportassistant.presentation.reset_password.ui.PasswordVerificationEmailScreen
import com.example.sportassistant.presentation.reset_password.ui.ResetPasswordEmailScreen
import com.example.sportassistant.presentation.reset_password.ui.ResetPasswordScreen
import com.example.sportassistant.presentation.reset_password.viewmodel.ResetPasswordViewModel
import com.example.sportassistant.presentation.settings.ui.SettingsScreen
import com.example.sportassistant.presentation.sfp_graphic.ui.SFPResultsGraphicScreen
import com.example.sportassistant.presentation.sfp_result_add.ui.SFPResultAddScreen
import com.example.sportassistant.presentation.sfp_results.ui.SFPResultsScreen
import com.example.sportassistant.presentation.sfp_results_info.ui.SFPResultsInfoScreen
import com.example.sportassistant.presentation.start.ui.StartScreen
import com.example.sportassistant.presentation.theme.SportAssistantTheme
import com.example.sportassistant.presentation.train_diary.ui.TrainDiaryMainScreen
import com.example.sportassistant.presentation.trainig_camps_add.ui.TrainingCampAddScreen
import com.example.sportassistant.presentation.training_camp_info.ui.TrainingCampInfoScreen
import com.example.sportassistant.presentation.training_camps_calendar.ui.TrainingCampsAllDaysScreen
import com.example.sportassistant.presentation.training_camps_calendar.ui.TrainingCampsCalendarScreen
import com.example.sportassistant.presentation.training_camps_day.ui.TrainingCampDayScreen
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.verification.ui.VerificationScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

open class Route(val route: String)

sealed class AuthRoutes {
    data object Start : Route("start")
    data object RegistrationStart : Route("registration_start")
    data object Verification : Route("email_verification")
    data object RegistrationProfile : Route("registration_profile")
    data object RegistrationCoach : Route("registration_coach")
    data object LogIn : Route("login")
    data object ResetPasswordEmail : Route("reset_password_email")
    data object ResetPasswordCode : Route("reset_password_code")
    data object ResetPassword : Route("reset_password")
}

sealed class GraphRoutes {
    data object AuthNav : Route("AUTH_GRAPH")
    data object HomeNav : Route("HOME_GRAPH")
}

sealed class HomeRoutes {
    data object Loading: Route("loading")
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
    data object Notes : Route("notes")
    data object NotesAdd : Route("notes_add")
    data object NotesInfo : Route("notes_info")
    data object MedExamination : Route("med_examination")
    data object MedExaminationAdd : Route("med_examination_add")
    data object MedExaminationInfo : Route("med_examination_info")
    data object ComprehensiveExamination : Route("comprehensive_examination")
    data object ComprehensiveExaminationAdd : Route("comprehensive_examination_add")
    data object ComprehensiveExaminationInfo : Route("comprehensive_examination_info")
    data object AnthropometricParams : Route("anthropometric_params_results")
    data object AnthropometricParamsAdd : Route("anthropometric_params_add")
    data object AnthropometricParamsInfo : Route("anthropometric_params_info")
    data object AnthropometricParamsGraphic : Route("anthropometric_params_graphic")
    data object SFPResultsGraphic : Route("sfp_results_graphic")
    data object OFPResultsGraphic : Route("ofp_results_graphic")
    data object TrainDiary : Route("train_diary")
    data object Sleep : Route("train_diary")
    data object Food : Route("train_diary")
    data object Preparations : Route("train_diary")
    data object Activity : Route("train_diary")
}

@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController(),
    profileInfoViewModel: SetProfileInfoViewModel = koinViewModel(),
    registrationViewModel: RegistrationViewModel = koinViewModel(),
    resetPasswordViewModel: ResetPasswordViewModel = koinViewModel(),
    titleViewModel: TitleViewModel = viewModel(),
) {
    val preferences: UserPreferencesRepository = get()
    var startDestination = GraphRoutes.AuthNav.route
    val isUserLoggedIn by preferences.isLoggedIn().collectAsState(initial = false)
    if (isUserLoggedIn) {
        startDestination = HomeRoutes.Loading.route
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
                profileInfoViewModel = profileInfoViewModel,
                registrationViewModel = registrationViewModel,
                resetPasswordViewModel = resetPasswordViewModel,
            )
            composable(route = HomeRoutes.Loading.route) {
                LaunchedEffect(Unit) {
                    profileInfoViewModel.isProfileFilled()
                }
                val profileState by profileInfoViewModel.isProfileFilled.observeAsState()
                when (profileState) {
                    is ApiResponse.Success -> {
                        val isFilled = (profileState as ApiResponse.Success<Boolean?>).data ?: false
                        navController.navigate(
                            if (isFilled)
                                GraphRoutes.HomeNav.route
                            else
                                AuthRoutes.RegistrationProfile.route
                        ) {
                            popUpTo(0)
                        }
                    }
                    is ApiResponse.Loading -> {
                        Loader()
                    }
                    else -> {}
                }
            }
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
    logout: () -> Unit,
) {
    val profileInfoViewModel: ProfileInfoViewModel = koinViewModel()
    val profileViewModel: ProfileViewModel = viewModel()
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
                navController = navController,
            )
        }
        composable(route = HomeRoutes.TrainingCamps.route) {
            TrainingCampsCalendarScreen(
                tabsViewModel = trainingCampsTabsViewModel,
                navController = navController,
            )
        }
        composable(route = HomeRoutes.CompetitionAllDaysNav.route){
            CompetitionAllDaysScreen(
                navController = navController,
                titleViewModel = titleViewModel,
            )
        }
        composable(route = HomeRoutes.TrainingCampsAllDaysNav.route){
            TrainingCampsAllDaysScreen(
                navController = navController,
                titleViewModel = titleViewModel,
            )
        }
        composable(route = HomeRoutes.CompetitionDay.route){
            CompetitionDayScreen()
        }
        composable(route = HomeRoutes.TrainingCampsDay.route){
            TrainingCampDayScreen()
        }
        composable(route = HomeRoutes.CompetitionResult.route){
            CompetitionResultScreen()
        }
        composable(route = HomeRoutes.CompetitionAdd.route){
            CompetitionAddScreen(
                navController = navController,
            )
        }
        composable(route = HomeRoutes.TrainingCampsAdd.route){
            TrainingCampAddScreen(
                navController = navController,
            )
        }
        composable(route = HomeRoutes.CompetitionInfo.route) {
            CompetitionInfoScreen()
        }
        composable(route = HomeRoutes.TrainingCampsInfo.route) {
            TrainingCampInfoScreen()
        }
        composable(route = HomeRoutes.Calendar.route) {
            CalendarScreen(
                navController = navController,
                titleViewModel = titleViewModel
            )
        }
        composable(route = HomeRoutes.OFPResults.route) {
            OFPResultsScreen(
                navController = navController,
                titleViewModel = titleViewModel,
            )
        }
        composable(route = HomeRoutes.OFPResultsAdd.route) {
            OFPResultAddScreen(
                navController = navController,
            )
        }
        composable(route = HomeRoutes.OFPResultsInfo.route) {
            OFPResultsInfoScreen()
        }
        composable(route = HomeRoutes.SFPResults.route) {
            SFPResultsScreen(
                navController = navController,
                titleViewModel = titleViewModel,
            )
        }
        composable(route = HomeRoutes.SFPResultsAdd.route) {
            SFPResultAddScreen(
                navController = navController,
            )
        }
        composable(route = HomeRoutes.SFPResultsInfo.route) {
            SFPResultsInfoScreen()
        }
        composable(route = HomeRoutes.AnthropometricParams.route) {
            AnthropometricParamsScreen(
                navController = navController,
                titleViewModel = titleViewModel,
            )
        }
        composable(route = HomeRoutes.Notes.route) {
            NotesScreen(
                navController = navController,
                titleViewModel = titleViewModel,
            )
        }
        composable(route = HomeRoutes.NotesAdd.route) {
            NoteAddScreen(
                navController = navController,
            )
        }
        composable(route = HomeRoutes.NotesInfo.route) {
            NoteInfoScreen()
        }
        composable(route = HomeRoutes.MedExamination.route) {
            MedExaminationScreen(
                navController = navController,
                titleViewModel = titleViewModel,
            )
        }
        composable(route = HomeRoutes.MedExaminationAdd.route) {
            MedExaminationAddScreen(
                navController = navController,
            )
        }
        composable(route = HomeRoutes.MedExaminationInfo.route) {
            MedExaminationInfoScreen()
        }
        composable(route = HomeRoutes.ComprehensiveExamination.route) {
            ComprehensiveExaminationScreen(
                navController = navController,
                titleViewModel = titleViewModel,
            )
        }
        composable(route = HomeRoutes.ComprehensiveExaminationAdd.route) {
            ComprehensiveExaminationAddScreen(
                navController = navController,
            )
        }
        composable(route = HomeRoutes.ComprehensiveExaminationInfo.route) {
            ComprehensiveExaminationInfoScreen()
        }
        composable(route = HomeRoutes.AnthropometricParamsAdd.route) {
            AnthropometricParamsAddScreen(
                navController = navController,
            )
        }
        composable(route = HomeRoutes.AnthropometricParamsInfo.route) {
            AnthropometricParamsInfoScreen()
        }
        composable(route = HomeRoutes.AnthropometricParamsGraphic.route) {
            AnthropometricParamsGraphicScreen()
        }
        composable(route = HomeRoutes.OFPResultsGraphic.route) {
            OFPResultsGraphicScreen()
        }
        composable(route = HomeRoutes.SFPResultsGraphic.route) {
            SFPResultsGraphicScreen()
        }
        composable(route = HomeRoutes.TrainDiary.route) {
            TrainDiaryMainScreen(
                navController = navController,
            )
        }
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    profileInfoViewModel: SetProfileInfoViewModel,
    registrationViewModel: RegistrationViewModel,
    resetPasswordViewModel: ResetPasswordViewModel
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
                },
                onForgotPasswordButtonClick = {
                    navigateTo(AuthRoutes.ResetPasswordEmail, navController)
                }
            )
        }
        composable(route = AuthRoutes.ResetPasswordEmail.route) {
            ResetPasswordEmailScreen(
                navController = navController,
                viewModel = resetPasswordViewModel,
            )
        }
        composable(route = AuthRoutes.ResetPasswordCode.route) {
            PasswordVerificationEmailScreen(
                navController = navController,
                viewModel = resetPasswordViewModel,
            )
        }
        composable(route = AuthRoutes.ResetPassword.route) {
            ResetPasswordScreen(
                navController = navController,
                viewModel = resetPasswordViewModel,
            )
        }
        composable(route = AuthRoutes.RegistrationStart.route){
            RegistrationCreateAccountScreen(
                viewModel = registrationViewModel,
                onContinueRegistrationButtonClick = {
                    Log.d("Navigation", "to -> Verification")
                    navigateTo(AuthRoutes.Verification, navController)
                }
            )
        }
        composable(route = AuthRoutes.Verification.route){
            VerificationScreen(
                viewModel = registrationViewModel,
                onContinueRegistrationButtonClick = {
                    Log.d("Navigation", "to -> RegistrationProfile")
                    navController.navigate(AuthRoutes.RegistrationProfile.route) {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(route = AuthRoutes.RegistrationCoach.route){
            RegistrationCoachScreen(
                viewModel = profileInfoViewModel,
                onFinishRegistrationButtonClick = {
                    Log.d("Navigation", "to -> Home")
                    navigateTo(GraphRoutes.HomeNav, navController)
                }
            )
        }
        composable(route = AuthRoutes.RegistrationProfile.route){
            RegistrationProfileScreen(
                viewModel = profileInfoViewModel,
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
