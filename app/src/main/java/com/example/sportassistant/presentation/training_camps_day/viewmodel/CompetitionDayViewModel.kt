package com.example.sportassistant.presentation.training_camps_day.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.TrainingCampsRepository
import com.example.sportassistant.data.schemas.training_camp_day.requests.TrainingCampDayUpdateRequest
import com.example.sportassistant.domain.model.TrainingCampDay
import com.example.sportassistant.presentation.training_camps_day.domain.TrainingCampDayUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class TrainingCampDayViewModel(
    private val trainingCampsRepository: TrainingCampsRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<TrainingCampDayUiState>(TrainingCampDayUiState(
        notes = "",
        goals = ""
    ))
    val uiState: StateFlow<TrainingCampDayUiState> = _uiState.asStateFlow()

    private val _updateCampDayResponse = MutableLiveData<ApiResponse<TrainingCampDay?>?>()
    val updateCampDayResponse = _updateCampDayResponse

    private val _getCampDayResponse = MutableLiveData<ApiResponse<TrainingCampDay?>?>()
    val getCampDayResponse = _getCampDayResponse

    fun getCampsDay(campId: UUID, day: LocalDate) = baseRequest(
        _getCampDayResponse
    ) {
        trainingCampsRepository.getTrainingCampDay(
            campId = campId,
            day = day,
        )
    }

    fun updateCampDay(campDay: TrainingCampDayUpdateRequest, campId: UUID) = baseRequest(
        _updateCampDayResponse
    ) {
        trainingCampsRepository.updateTrainingCampDay(campDay, campId)
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

    fun clearUpdate() {
        _updateCampDayResponse.postValue(null)
    }
}