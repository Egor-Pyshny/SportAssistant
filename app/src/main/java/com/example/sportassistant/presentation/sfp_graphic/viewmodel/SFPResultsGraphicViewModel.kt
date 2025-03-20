package com.example.sportassistant.presentation.sfp_graphic.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.ofp_graphic.domain.OFPResultsGraphicUiState
import com.example.sportassistant.presentation.sfp_graphic.domain.SFPResultsGraphicUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class SFPResultsGraphicViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<SFPResultsGraphicUiState>(
        SFPResultsGraphicUiState()
    )
    val uiState: StateFlow<SFPResultsGraphicUiState> = _uiState.asStateFlow()

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

    fun setCategory(category: String) {
        _uiState.update { currentState ->
            currentState.copy(
                category = category
            )
        }
    }

    fun setSelectedCategory(categoryId: UUID?) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCategoryId = categoryId
            )
        }
    }
}