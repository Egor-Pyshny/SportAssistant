package com.example.sportassistant.presentation.reset_password.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.AuthRepository
import com.example.sportassistant.data.repository.UserRepository
import com.example.sportassistant.data.schemas.auth.requests.ChangePasswordRequest
import com.example.sportassistant.data.schemas.auth.requests.ForgotPasswordRequest
import com.example.sportassistant.data.schemas.auth.requests.ResendCodeRequest
import com.example.sportassistant.data.schemas.auth.requests.SetProfileDataRequest
import com.example.sportassistant.data.schemas.auth.requests.VerifyEmailRequest
import com.example.sportassistant.presentation.registration.domain.model.ProfileInfoUiState
import com.example.sportassistant.presentation.reset_password.domain.ResetPasswordUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ResetPasswordViewModel(
    private val authRepository: AuthRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<ResetPasswordUiState>(ResetPasswordUiState())
    val uiState: StateFlow<ResetPasswordUiState> = _uiState.asStateFlow()

    private val _getCodeResponse = MutableLiveData<ApiResponse<Void?>>()
    val getCodeResponse = _getCodeResponse

    private val _checkCodeResponse = MutableLiveData<ApiResponse<Void?>>()
    val checkCodeResponse = _checkCodeResponse

    private val _changePasswordResponse = MutableLiveData<ApiResponse<Void?>>()
    val changePasswordResponse = _changePasswordResponse

    private val _resendCodeResponse = MutableLiveData<ApiResponse<Void?>>()
    val resendCodeResponse = _resendCodeResponse

    fun resendCode(data: ResendCodeRequest) = baseRequest(
        _resendCodeResponse
    ) {
        authRepository.resendPasswordCode(data)
    }

    fun getCode(data: ForgotPasswordRequest) = baseRequest(
        _getCodeResponse
    ) {
        authRepository.getPasswordCode(data)
    }

    fun checkCode(data: VerifyEmailRequest) = baseRequest(
        _checkCodeResponse
    ) {
        authRepository.checkPasswordCode(data)
    }

    fun changePassword(data: ChangePasswordRequest) = baseRequest(
        _changePasswordResponse
    ) {
        authRepository.changePassword(data)
    }

    fun setEmail(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
            )
        }
    }

    fun setCode(code: String) {
        _uiState.update { currentState ->
            currentState.copy(
                code = code,
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

    fun setRepeatPassword(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                repeatPassword = password,
            )
        }
    }

    fun setUserMailError(error: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                userMailError = error,
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

    fun setRepeatPasswordVisibility(visibility: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                repeatPasswordVisibility = visibility,
            )
        }
    }
}