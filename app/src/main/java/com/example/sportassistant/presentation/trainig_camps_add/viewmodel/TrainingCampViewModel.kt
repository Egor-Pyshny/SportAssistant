package com.example.sportassistant.presentation.trainig_camps_add.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.trainig_camps_add.domain.TrainingCampUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class TrainingCampViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<TrainingCampUiState>(TrainingCampUiState())
    val uiState: StateFlow<TrainingCampUiState> = _uiState.asStateFlow()

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

    fun setGoals(goals: String) {
        _uiState.update { currentState ->
            currentState.copy(
                goals = goals
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