package com.example.sportassistant.presentation.sfp_result_add.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.ofp_result_add.domain.OFPResultModelUiState
import com.example.sportassistant.presentation.sfp_result_add.domain.SFPResultModelUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class SFPResultAddViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<SFPResultModelUiState>(SFPResultModelUiState())
    val uiState: StateFlow<SFPResultModelUiState> = _uiState.asStateFlow()

    fun setDate(date: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(
                date = date
            )
        }
    }

    fun setCategory(category: UUID) {
        _uiState.update { currentState ->
            currentState.copy(
                categoryId = category
            )
        }
    }

    fun setResult(result: String) {
        _uiState.update { currentState ->
            currentState.copy(
                result = result
            )
        }
    }

    fun setNotes(notes: String) {
        _uiState.update { currentState ->
            currentState.copy(
                notes = notes
            )
        }
    }

    fun setGoals(goals: String) {
        _uiState.update { currentState ->
            currentState.copy(
                goals = goals
            )
        }
    }
}