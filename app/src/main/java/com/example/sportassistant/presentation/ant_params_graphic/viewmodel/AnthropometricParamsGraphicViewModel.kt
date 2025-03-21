package com.example.sportassistant.presentation.ant_params_graphic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportassistant.data.repository.AnthropometricParamsRepository
import com.example.sportassistant.domain.enums.AnthropometricParamsMeasures
import com.example.sportassistant.domain.model.GraphicPoint
import com.example.sportassistant.presentation.ant_params_graphic.domain.AnthropometricParamsGraphicUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class AnthropometricParamsGraphicViewModel(
    private val anthropometricParamsRepository: AnthropometricParamsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<AnthropometricParamsGraphicUiState>(AnthropometricParamsGraphicUiState())
    val uiState: StateFlow<AnthropometricParamsGraphicUiState> = _uiState.asStateFlow()

    private val _getGraphicDataResponse = MutableLiveData<ApiResponse<List<GraphicPoint>?>?>()
    val getGraphicDataResponse = _getGraphicDataResponse

    fun getGraphicData(
        startDate: LocalDate,
        endDate: LocalDate,
        category: AnthropometricParamsMeasures
    ) = baseRequest(
        _getGraphicDataResponse
    ) {
        anthropometricParamsRepository.getGraphicData(
            startDate = startDate,
            endDate = endDate,
            category = category,
        )
    }

    fun clearGraphicDataResponse() {
        _getGraphicDataResponse.postValue(null)
    }

    fun setStartDate(startDate: LocalDate?) {
        _uiState.update { currentState ->
            currentState.copy(
                startDate = startDate
            )
        }
    }

    fun setEndDate(endDate: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(
                endDate = endDate
            )
        }
    }

    fun setDatePeriod(datePeriod: String) {
        _uiState.update { currentState ->
            currentState.copy(
                datePeriod = datePeriod
            )
        }
    }

    fun setMeasureString(measure: String) {
        _uiState.update { currentState ->
            currentState.copy(
                measureString = measure
            )
        }
    }

    fun setMeasure(measure: AnthropometricParamsMeasures?) {
        _uiState.update { currentState ->
            currentState.copy(
                measure = measure
            )
        }
    }
}