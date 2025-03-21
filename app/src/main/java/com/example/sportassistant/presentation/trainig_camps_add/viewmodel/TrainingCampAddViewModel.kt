package com.example.sportassistant.presentation.trainig_camps_add.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.TrainingCampsRepository
import com.example.sportassistant.data.schemas.training_camps.requests.CreateTrainingCampRequest
import com.example.sportassistant.presentation.trainig_camps_add.domain.TrainingCampUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class TrainingCampAddViewModel(
    private val trainingCampsRepository: TrainingCampsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<TrainingCampUiState>(TrainingCampUiState())
    val uiState: StateFlow<TrainingCampUiState> = _uiState.asStateFlow()

    private val _campAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val campAddResponse = _campAddResponse

    fun createCamp(camp: CreateTrainingCampRequest) = baseRequest(
        _campAddResponse
    ) {
        trainingCampsRepository.createTrainingCamp(camp)
    }

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