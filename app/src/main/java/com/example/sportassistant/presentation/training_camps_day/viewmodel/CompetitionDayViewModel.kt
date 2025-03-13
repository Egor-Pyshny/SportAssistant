package com.example.sportassistant.presentation.training_camps_day.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.competition_day.domain.CompetitionDayUiState
import com.example.sportassistant.presentation.training_camps_day.domain.TrainingCampDayUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TrainingCampDayViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<TrainingCampDayUiState>(TrainingCampDayUiState(
        notes = "",
        goals = ""
    ))
    val uiState: StateFlow<TrainingCampDayUiState> = _uiState.asStateFlow()

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