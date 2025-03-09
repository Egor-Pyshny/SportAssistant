package com.example.sportassistant.presentation.competition_day.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.competition_day.domain.CompetitionDayUiState
import com.example.sportassistant.presentation.competition_day.domain.CompetitionResultUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CompetitionResultViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<CompetitionResultUiState>(CompetitionResultUiState(
        notes = "",
        result = ""
    ))
    val uiState: StateFlow<CompetitionResultUiState> = _uiState.asStateFlow()

    fun setNotes(notes: String) {
        _uiState.update { currentState ->
            currentState.copy(
                notes = notes
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
}