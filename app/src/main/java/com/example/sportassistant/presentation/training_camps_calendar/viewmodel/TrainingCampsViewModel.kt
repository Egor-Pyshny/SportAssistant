package com.example.sportassistant.presentation.training_camps_calendar.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.TrainingCampsRepository
import com.example.sportassistant.data.schemas.training_camp_day.requests.TrainingCampDayUpdateRequest
import com.example.sportassistant.data.schemas.training_camps.requests.CreateTrainingCampRequest
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.enums.CompetitionStatus
import com.example.sportassistant.domain.model.TrainingCamp
import com.example.sportassistant.domain.model.TrainingCampDay
import com.example.sportassistant.presentation.training_camps_calendar.domain.TrainingCampsUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class TrainingCampsViewModel(
    private val trainingCampsRepository: TrainingCampsRepository,
): BaseViewModel() {
    private val _getCampsCurrentResponse = MutableLiveData<ApiResponse<List<TrainingCamp>?>>()
    val getCampsCurrentResponse = _getCampsCurrentResponse

    private val _getCampsPrevResponse = MutableLiveData<ApiResponse<List<TrainingCamp>?>>()
    val getCampsPrevResponse = _getCampsPrevResponse

    private val _getCampsNextResponse = MutableLiveData<ApiResponse<List<TrainingCamp>?>>()
    val getCampsNextResponse = _getCampsNextResponse

    private val _deleteCampResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteCampResponse = _deleteCampResponse

    fun clearDeleteResponse() {
        _deleteCampResponse.postValue(null)
    }

    fun deleteCamp(id: UUID) = baseRequest(
        _deleteCampResponse
    ) {
        trainingCampsRepository.deleteTrainingCamp(id)
    }

    fun getCamps(index: Int) {
        val state = ApplicationState.getState()
        when (index) {
            0 -> {
                baseRequest(
                    _getCampsPrevResponse
                ) {
                    trainingCampsRepository.getTrainingCamps(
                        date = LocalDate.now(),
                        status = CompetitionStatus.past
                    )
                }
            }
            1 -> {
                baseRequest(
                    _getCampsCurrentResponse
                ) {
                    trainingCampsRepository.getTrainingCamps(
                        date = LocalDate.now(),
                        status = CompetitionStatus.current
                    )
                }
            }
            2 -> {
                baseRequest(
                    _getCampsNextResponse
                ) {
                    trainingCampsRepository.getTrainingCamps(
                        date = LocalDate.now(),
                        status = CompetitionStatus.next
                    )
                }
            }
            else -> {
                throw Exception()
            }
        }
    }
}