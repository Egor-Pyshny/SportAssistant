package com.example.sportassistant.presentation.applayout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportassistant.data.repository.UserPreferencesRepository
import com.example.sportassistant.domain.model.AppDispatchers
import com.example.sportassistant.presentation.applayout.domain.AppLayoutUiState
import com.example.sportassistant.presentation.login.domain.LogInUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppLayoutViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val AppDispatchers: AppDispatchers,
): ViewModel() {
    private val _uiState = MutableStateFlow<AppLayoutUiState>(AppLayoutUiState())
    val uiState: StateFlow<AppLayoutUiState> = _uiState.asStateFlow()

    fun loadTheme() {
        viewModelScope.launch(AppDispatchers.io) {
            userPreferencesRepository.isDarkTheme.collect { isDark ->
                setTheme(isDark)
            }
        }
    }

    fun setTheme(isDark: Boolean) {
        try {
            viewModelScope.launch(AppDispatchers.io) {
                userPreferencesRepository.saveLayoutPreference(isDark)
            }
            _uiState.update { currentState ->
                currentState.copy(
                    isDark = isDark,
                )
            }
        } finally {}
    }
}