package com.example.sportassistant.presentation.registration.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.AuthRepository
import com.example.sportassistant.data.schemas.auth.requests.RegistrationRequest
import com.example.sportassistant.domain.model.Coach
import com.example.sportassistant.presentation.registration.domain.model.RegistrationUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class RegistrationViewModel(
    private val authRepository: AuthRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    private val _registrationResponse = MutableLiveData<ApiResponse<Void?>>()
    val registrationResponse = _registrationResponse

    fun registration(data: RegistrationRequest) = baseRequest(
        _registrationResponse
    ) {
        authRepository.registration(data)
    }

    fun setName(userName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                userName = userName,
            )
        }
    }

    fun setSurname(userSurname: String) {
        _uiState.update { currentState ->
            currentState.copy(
                userSurname = userSurname,
            )
        }
    }

    fun setMail(userMail: String) {
        _uiState.update { currentState ->
            currentState.copy(
                userMail = userMail,
            )
        }
    }

    fun setPassword(userPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(
                userPassword = userPassword,
            )
        }
    }

    fun setSportType(sportType: String) {
        _uiState.update { currentState ->
            currentState.copy(
                sportType = sportType,
            )
        }
    }

    fun setQualification(qualification: String) {
        _uiState.update { currentState ->
            currentState.copy(
                qualification = qualification,
            )
        }
    }

    fun setAddress(address: String) {
        _uiState.update { currentState ->
            currentState.copy(
                address = address,
            )
        }
    }

    fun setPhoneNumber(phoneNumber: String) {
        _uiState.update { currentState ->
            currentState.copy(
                phoneNumber = phoneNumber,
            )
        }
    }

    fun setGender(gender: String) {
        _uiState.update { currentState ->
            currentState.copy(
                gender = gender,
            )
        }
    }

    fun setCoach(coach: Coach?) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCoach = coach,
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

    fun setPasswordError(isError: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                userPasswordError = isError,
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