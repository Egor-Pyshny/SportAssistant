package com.example.sportassistant.presentation.ant_params.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.AnthropometricParamsRepository
import com.example.sportassistant.domain.model.AnthropometricParams
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import java.util.UUID

class AnthropometricParamsViewModel(
    private val anthropometricParamsRepository: AnthropometricParamsRepository,
): BaseViewModel() {
    private val _getAnthropometricParamsResponse = MutableLiveData<ApiResponse<List<AnthropometricParams>?>>()
    val getAnthropometricParamsResponse = _getAnthropometricParamsResponse

    private val _deleteAnthropometricParamsResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteAnthropometricParamsResponse = _deleteAnthropometricParamsResponse

    fun deleteAnthropometricParams(paramsId: UUID) = baseRequest(
        _deleteAnthropometricParamsResponse
    ) {
        anthropometricParamsRepository.deleteAnthropometricParams(
            id = paramsId,
        )
    }

    fun clearDeleteResponse() {
        _deleteAnthropometricParamsResponse.postValue(null)
    }

    fun getAnthropometricParams() {
        baseRequest(
            _getAnthropometricParamsResponse
        ) {
            anthropometricParamsRepository.getAnthropometricParams()
        }
    }
}