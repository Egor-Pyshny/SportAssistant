package com.example.sportassistant.presentation.registration.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.registration.domain.model.RegistrationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistrationViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

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

    fun setCoachFIO(fio: String) {
        _uiState.update { currentState ->
            currentState.copy(
                coachFIO = fio,
            )
        }
    }

    fun setCoachPhone(phone: String) {
        _uiState.update { currentState ->
            currentState.copy(
                coachFIO = phone,
            )
        }
    }

    fun setCoachInstitution(institution: String) {
        _uiState.update { currentState ->
            currentState.copy(
                coachInstitution = institution,
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