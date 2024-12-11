package com.example.sportassistant.di

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.data.repository.WindowSizeProvider
import com.example.sportassistant.presentation.applayout.viewmodel.testV
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModules = module {
    single { UserPreferencesRepository(androidContext()) }
    single { WindowSizeProvider() }
    viewModel{ testV() }
}