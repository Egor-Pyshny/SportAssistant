package com.example.sportassistant.presentation.ant_params_add.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.AnthropometricParamsRepository
import com.example.sportassistant.data.schemas.ant_params.requests.AnthropometricParamsCreateRequest
import com.example.sportassistant.presentation.ant_params_add.domain.AnthropometricParamsUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class AnthropometricParamsAddViewModel(
    private val anthropometricParamsRepository: AnthropometricParamsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<AnthropometricParamsUiState>(AnthropometricParamsUiState())
    val uiState: StateFlow<AnthropometricParamsUiState> = _uiState.asStateFlow()

    private val _anthropometricParamsAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val anthropometricParamsAddResponse = _anthropometricParamsAddResponse

    fun addAnthropometricParams(data: AnthropometricParamsCreateRequest) = baseRequest(
        _anthropometricParamsAddResponse
    ) {
        anthropometricParamsRepository.createAnthropometricParams(
            data = data
        )
    }

    fun setDate(date: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(
                date = date
            )
        }
    }

    fun setWeight(weight: String) {
        _uiState.update { currentState ->
            currentState.copy(
                weight = weight
            )
        }
    }

    fun setHeight(height: String) {
        _uiState.update { currentState ->
            currentState.copy(
                height = height
            )
        }
    }

    fun setChestCircumference(chestCircumference: String) {
        _uiState.update { currentState ->
            currentState.copy(
                chestCircumference = chestCircumference
            )
        }
    }
}