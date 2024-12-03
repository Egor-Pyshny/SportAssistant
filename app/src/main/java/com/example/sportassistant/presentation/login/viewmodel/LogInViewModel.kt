package com.example.sportassistant.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.login.domain.LogInUiState
import com.example.sportassistant.presentation.registration.domain.model.RegistrationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LogInViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<LogInUiState>(LogInUiState())
    val uiState: StateFlow<LogInUiState> = _uiState.asStateFlow()

    fun setEmail(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
            )
        }
    }

    fun setPassword(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
            )
        }
    }

    fun setUserMailError(isError: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                userMailError = isError,
            )
        }
    }

    fun setPasswordVisibility(visibility: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                passwordVisibility = visibility,
            )
        }
    }
}