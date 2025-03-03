package com.example.sportassistant.di

import com.example.sportassistant.data.repository.AuthRepository
import com.example.sportassistant.data.repository.CoachRepository
import com.example.sportassistant.data.repository.CompetitionRepository
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.data.repository.UserRepository
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.applayout.viewmodel.AppLayoutViewModel
import com.example.sportassistant.presentation.competition_calendar.viewmodel.CompetitionViewModel
import com.example.sportassistant.presentation.login.viewmodel.LogInViewModel
import com.example.sportassistant.presentation.profile.viewmodel.ProfileInfoViewModel
import com.example.sportassistant.presentation.registration.viewmodel.CheckEmailViewModel
import com.example.sportassistant.presentation.registration.viewmodel.CoachViewModel
import com.example.sportassistant.presentation.registration.viewmodel.RegistrationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModules = module {
    single {
        UserPreferencesRepository(
            androidContext(),
            get()
        )
    }
    single {
        WindowSizeProvider()
    }
    viewModel{
        AppLayoutViewModel(
            userPreferencesRepository = get(),
            appDispatchers = get()
        )
    }
    factory { AuthRepository(get()) }
    factory { UserRepository(get()) }
    factory { CoachRepository(get()) }
    factory { CompetitionRepository(get()) }
    viewModel{
        LogInViewModel(
            authRepository = get(),
        )
    }
    viewModel{
        CheckEmailViewModel(
            userRepository = get(),
        )
    }
    viewModel{
        RegistrationViewModel(
            authRepository = get(),
        )
    }
    viewModel{
        CoachViewModel(
            coachRepository = get(),
        )
    }
    viewModel{
        ProfileInfoViewModel(
            userRepository = get(),
        )
    }
    viewModel{
        CompetitionViewModel(
            competitionRepository = get(),
        )
    }
}