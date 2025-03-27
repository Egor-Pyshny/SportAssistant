package com.example.sportassistant.presentation.registration.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.UserRepository
import com.example.sportassistant.data.schemas.auth.requests.SetProfileDataRequest
import com.example.sportassistant.domain.model.Coach
import com.example.sportassistant.presentation.registration.domain.model.ProfileInfoUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SetProfileInfoViewModel(
    private val userRepository: UserRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<ProfileInfoUiState>(ProfileInfoUiState())
    val uiState: StateFlow<ProfileInfoUiState> = _uiState.asStateFlow()

    private val _setInfoResponse = MutableLiveData<ApiResponse<Void?>>()
    val setInfoResponse = _setInfoResponse

    fun setInfo(data: SetProfileDataRequest) = baseRequest(
        _setInfoResponse
    ) {
        userRepository.setInfo(data)
    }

    private val _isProfileFilled = MutableLiveData<ApiResponse<Boolean?>>()
    val isProfileFilled = _isProfileFilled

    fun isProfileFilled() = baseRequest(
        _isProfileFilled
    ) {
        userRepository.isProfileFilled()
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
}