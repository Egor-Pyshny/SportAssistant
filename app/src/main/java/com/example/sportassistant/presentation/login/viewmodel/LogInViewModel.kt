package com.example.sportassistant.presentation.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.AuthRepository
import com.example.sportassistant.data.schemas.auth.requests.LoginRequest
import com.example.sportassistant.presentation.login.domain.LogInUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LogInViewModel(
    private val authRepository: AuthRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<LogInUiState>(LogInUiState())
    val uiState: StateFlow<LogInUiState> = _uiState.asStateFlow()

    private val _loginResponse = MutableLiveData<ApiResponse<Void?>>()
    val loginResponse = _loginResponse

    fun login(data: LoginRequest) = baseRequest(
        _loginResponse
    ) {
        authRepository.login(data)
    }

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