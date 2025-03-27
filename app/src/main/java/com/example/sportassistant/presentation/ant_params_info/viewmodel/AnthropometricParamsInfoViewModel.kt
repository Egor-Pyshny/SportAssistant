package com.example.sportassistant.presentation.ant_params_info.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.AnthropometricParamsRepository
import com.example.sportassistant.data.schemas.ant_params.requests.AnthropometricParamsCreateRequest
import com.example.sportassistant.domain.model.AnthropometricParams
import com.example.sportassistant.presentation.ant_params_add.domain.AnthropometricParamsUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class AnthropometricParamsInfoViewModel(
    private val anthropometricParamsRepository: AnthropometricParamsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<AnthropometricParamsUiState>(AnthropometricParamsUiState())
    val uiState: StateFlow<AnthropometricParamsUiState> = _uiState.asStateFlow()

    private val _getAnthropometricParamsInfoResponse = MutableLiveData<ApiResponse<AnthropometricParams?>>()
    val getAnthropometricParamsInfoResponse = _getAnthropometricParamsInfoResponse

    private val _updateAnthropometricParamsResponse = MutableLiveData<ApiResponse<Void?>?>()
    val updateAnthropometricParamsResponse = _updateAnthropometricParamsResponse

    fun clearUpdateResponse() {
        _updateAnthropometricParamsResponse.postValue(null)
    }

    fun updateAnthropometricParams(data: AnthropometricParamsCreateRequest, paramsId: UUID) = baseRequest(
        _updateAnthropometricParamsResponse
    ) {
        anthropometricParamsRepository.updateAnthropometricParams(
            data = data,
            id = paramsId,
        )
    }

    fun getAnthropometricParamsInfo(id: UUID) = baseRequest(
        _getAnthropometricParamsInfoResponse
    ) {
        anthropometricParamsRepository.getAnthropometricParamsInfo(
            id = id
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