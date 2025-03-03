package com.example.sportassistant.presentation.competition_add.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.competition_add.domain.CompetitionAddUiState
import com.example.sportassistant.presentation.competition_calendar.domain.CompetitionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class CompetitionAddViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<CompetitionAddUiState>(CompetitionAddUiState())
    val uiState: StateFlow<CompetitionAddUiState> = _uiState.asStateFlow()

    fun setStartDate(startDate: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(
                startDate = startDate
            )
        }
    }

    fun setEndDate(endDate: LocalDate?) {
        _uiState.update { currentState ->
            currentState.copy(
                endDate = endDate
            )
        }
    }

    fun setName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                name = name
            )
        }
    }

    fun setLocation(location: String) {
        _uiState.update { currentState ->
            currentState.copy(
                location = location
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
}