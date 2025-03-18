package com.example.sportassistant.presentation.ant_params_graphic.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.domain.enums.AnthropometricParamsMeasures
import com.example.sportassistant.presentation.ant_params_graphic.domain.AnthropometricParamsGraphicUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class AnthropometricParamsGraphicViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<AnthropometricParamsGraphicUiState>(AnthropometricParamsGraphicUiState())
    val uiState: StateFlow<AnthropometricParamsGraphicUiState> = _uiState.asStateFlow()

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