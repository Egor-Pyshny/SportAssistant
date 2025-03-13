package com.example.sportassistant.presentation.training_camps_calendar.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.TrainingCampsRepository
import com.example.sportassistant.data.schemas.competition.requests.CreateCompetitionRequest
import com.example.sportassistant.data.schemas.competition_day.requests.CompetitionDayUpdateRequest
import com.example.sportassistant.data.schemas.competition_result.requests.CompetitionResultUpdateRequest
import com.example.sportassistant.data.schemas.training_camp_day.requests.TrainingCampDayUpdateRequest
import com.example.sportassistant.data.schemas.training_camps.requests.CreateTrainingCampRequest
import com.example.sportassistant.domain.enums.CompetitionStatus
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.CompetitionDay
import com.example.sportassistant.domain.model.CompetitionResult
import com.example.sportassistant.domain.model.TrainingCamp
import com.example.sportassistant.domain.model.TrainingCampDay
import com.example.sportassistant.presentation.competition_calendar.domain.CompetitionUiState
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
    private val _uiState = MutableStateFlow<TrainingCampsUiState>(TrainingCampsUiState())
    val uiState: StateFlow<TrainingCampsUiState> = _uiState.asStateFlow()

    private val _campAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val campAddResponse = _campAddResponse

    private val _getCampsCurrentResponse = MutableLiveData<ApiResponse<List<TrainingCamp>?>>()
    val getCampsCurrentResponse = _getCampsCurrentResponse

    private val _getCampsPrevResponse = MutableLiveData<ApiResponse<List<TrainingCamp>?>>()
    val getCampsPrevResponse = _getCampsPrevResponse

    private val _getCampsNextResponse = MutableLiveData<ApiResponse<List<TrainingCamp>?>>()
    val getCampsNextResponse = _getCampsNextResponse

    private val _getCampsInfoResponse = MutableLiveData<ApiResponse<TrainingCamp?>>()
    val getCampsInfoResponse = _getCampsInfoResponse

    private val _updateCampResponse = MutableLiveData<ApiResponse<TrainingCamp?>?>()
    val updateCampResponse = _updateCampResponse

    private val _updateCampDayResponse = MutableLiveData<ApiResponse<TrainingCampDay?>?>()
    val updateCampDayResponse = _updateCampDayResponse

    private val _deleteCampResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteCampResponse = _deleteCampResponse

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

    fun clearUpdateResponse() {
        _updateCampResponse.postValue(null)
    }

    fun clearUpdateDayResponse() {
        _updateCampDayResponse.postValue(null)
    }

    fun clearDeleteResponse() {
        _deleteCampResponse.postValue(null)
    }

    fun clearCreateResponse() {
        _campAddResponse.postValue(null)
    }

    fun createCamp(camp: CreateTrainingCampRequest) = baseRequest(
        _campAddResponse
    ) {
        trainingCampsRepository.createTrainingCamp(camp)
    }

    fun updateCampDay(campDay: TrainingCampDayUpdateRequest, campId: UUID) = baseRequest(
        _updateCampDayResponse
    ) {
        trainingCampsRepository.updateTrainingCampDay(campDay, campId)
    }

    fun updateCamp(camp: CreateTrainingCampRequest, id: UUID) = baseRequest(
        _updateCampResponse
    ) {
        trainingCampsRepository.updateTrainingCamps(camp, id)
    }

    fun deleteCamp(id: UUID) = baseRequest(
        _deleteCampResponse
    ) {
        trainingCampsRepository.deleteTrainingCamp(id)
    }

    fun getCamp(id: UUID) = baseRequest(
        _getCampsInfoResponse
    ) {
        trainingCampsRepository.getTrainingCamp(id)
    }

    fun getCamps(index: Int) {
        when (index) {
            0 -> {
                if (uiState.value.lastFetched == null || uiState.value.lastFetched != LocalDate.now()) {
                    uiState.value.lastFetched = LocalDate.now()
                    baseRequest(
                        _getCampsPrevResponse
                    ) {
                        trainingCampsRepository.getTrainingCamps(
                            date = LocalDate.now(),
                            status = CompetitionStatus.past
                        )
                    }
                }
            }
            1 -> {
                if (uiState.value.shouldUpdateCurrent || uiState.value.lastFetched != LocalDate.now()) {
                    uiState.value.shouldUpdateCurrent = false
                    baseRequest(
                        _getCampsCurrentResponse
                    ) {
                        trainingCampsRepository.getTrainingCamps(
                            date = LocalDate.now(),
                            status = CompetitionStatus.current
                        )
                    }
                }
            }
            2 -> {
                if (uiState.value.shouldUpdateNext || uiState.value.lastFetched != LocalDate.now()) {
                    uiState.value.shouldUpdateNext = false
                    baseRequest(
                        _getCampsNextResponse
                    ) {
                        trainingCampsRepository.getTrainingCamps(
                            date = LocalDate.now(),
                            status = CompetitionStatus.next
                        )
                    }
                }
            }
            else -> {
                throw Exception()
            }
        }
    }

    fun setSelectedCamp(camp: TrainingCamp) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCamp = camp
            )
        }
    }

    fun shouldUpdateNext(flag: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                shouldUpdateNext = flag
            )
        }
    }

    fun shouldUpdateCurrent(flag: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                shouldUpdateCurrent = flag
            )
        }
    }

    fun setLastFetched(date: LocalDate?) {
        _uiState.update { currentState ->
            currentState.copy(
                lastFetched = date
            )
        }
    }

    fun setSelectedDay(day: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedDay = day
            )
        }
    }
}