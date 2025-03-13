package com.example.sportassistant.presentation.ant_params.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.AnthropometricParamsRepository
import com.example.sportassistant.data.schemas.ant_params.requests.AnthropometricParamsCreateRequest
import com.example.sportassistant.domain.model.AnthropometricParams
import com.example.sportassistant.presentation.ant_params.domain.AnthropometricParamsUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class AnthropometricParamsViewModel(
    private val anthropometricParamsRepository: AnthropometricParamsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<AnthropometricParamsUiState>(AnthropometricParamsUiState())
    val uiState: StateFlow<AnthropometricParamsUiState> = _uiState.asStateFlow()

    private val _anthropometricParamsAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val anthropometricParamsAddResponse = _anthropometricParamsAddResponse

    private val _getAnthropometricParamsResponse = MutableLiveData<ApiResponse<List<AnthropometricParams>?>>()
    val getAnthropometricParamsResponse = _getAnthropometricParamsResponse

    private val _getAnthropometricParamsInfoResponse = MutableLiveData<ApiResponse<AnthropometricParams?>>()
    val getAnthropometricParamsInfoResponse = _getAnthropometricParamsInfoResponse

    private val _updateAnthropometricParamsResponse = MutableLiveData<ApiResponse<Void?>?>()
    val updateAnthropometricParamsResponse = _updateAnthropometricParamsResponse

    private val _deleteAnthropometricParamsResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteAnthropometricParamsResponse = _deleteAnthropometricParamsResponse

    fun deleteAnthropometricParams(paramsId: UUID) = baseRequest(
        _deleteAnthropometricParamsResponse
    ) {
        anthropometricParamsRepository.deleteAnthropometricParams(
            id = paramsId,
        )
    }

    fun clearCreateResponse() {
        _anthropometricParamsAddResponse.postValue(null)
    }

    fun clearUpdateResponse() {
        _updateAnthropometricParamsResponse.postValue(null)
    }

    fun clearDeleteResponse() {
        _deleteAnthropometricParamsResponse.postValue(null)
    }

    fun updateAnthropometricParams(data: AnthropometricParamsCreateRequest, paramsId: UUID) = baseRequest(
        _updateAnthropometricParamsResponse
    ) {
        anthropometricParamsRepository.updateAnthropometricParams(
            data = data,
            id = paramsId,
        )
    }

    fun addAnthropometricParams(data: AnthropometricParamsCreateRequest) = baseRequest(
        _anthropometricParamsAddResponse
    ) {
        anthropometricParamsRepository.createAnthropometricParams(
            data = data
        )
    }

    fun getAnthropometricParamsInfo(id: UUID) = baseRequest(
        _getAnthropometricParamsInfoResponse
    ) {
        anthropometricParamsRepository.getAnthropometricParamsInfo(
            id = id
        )
    }

    fun getAnthropometricParams() {
        if (uiState.value.shouldRefetch) {
            uiState.value.shouldRefetch = false
            baseRequest(
                _getAnthropometricParamsResponse
            ) {
                anthropometricParamsRepository.getAnthropometricParams()
            }
        }
    }

    fun setSelectedAnthropometricParams(params: AnthropometricParams) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedAnthropometricParams = params
            )
        }
    }

    fun setShouldRefetch(flag: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                shouldRefetch = flag
            )
        }
    }
}