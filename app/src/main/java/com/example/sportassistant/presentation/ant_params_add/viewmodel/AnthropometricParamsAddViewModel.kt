package com.example.sportassistant.presentation.ant_params_add.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.ant_params_add.domain.AnthropometricParamsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class AnthropometricParamsAddViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<AnthropometricParamsUiState>(AnthropometricParamsUiState())
    val uiState: StateFlow<AnthropometricParamsUiState> = _uiState.asStateFlow()

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