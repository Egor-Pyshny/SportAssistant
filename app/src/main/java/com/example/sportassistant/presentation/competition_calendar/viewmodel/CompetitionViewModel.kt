package com.example.sportassistant.presentation.competition_calendar.viewmodel

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportassistant.data.repository.CompetitionRepository
import com.example.sportassistant.data.repository.UserRepository
import com.example.sportassistant.data.schemas.competition.requests.CreateCompetitionRequest
import com.example.sportassistant.data.schemas.competition_day.requests.CompetitionDayUpdateRequest
import com.example.sportassistant.data.schemas.user.requests.CheckEmailRequest
import com.example.sportassistant.domain.enums.CompetitionStatus
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.domain.model.CompetitionDay
import com.example.sportassistant.presentation.competition_calendar.domain.CompetitionUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

class CompetitionViewModel(
    private val competitionRepository: CompetitionRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<CompetitionUiState>(CompetitionUiState())
    val uiState: StateFlow<CompetitionUiState> = _uiState.asStateFlow()

    private val _competitionsAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val competitionsAddResponse = _competitionsAddResponse

    private val _getCompetitionsCurrentResponse = MutableLiveData<ApiResponse<List<Competition>?>>()
    val getCompetitionsCurrentResponse = _getCompetitionsCurrentResponse

    private val _getCompetitionsPrevResponse = MutableLiveData<ApiResponse<List<Competition>?>>()
    val getCompetitionsPrevResponse = _getCompetitionsPrevResponse

    private val _getCompetitionsNextResponse = MutableLiveData<ApiResponse<List<Competition>?>>()
    val getCompetitionsNextResponse = _getCompetitionsNextResponse

    private val _getCompetitionInfoResponse = MutableLiveData<ApiResponse<Competition?>>()
    val getCompetitionInfoResponse = _getCompetitionInfoResponse

    private val _updateCompetitionResponse = MutableLiveData<ApiResponse<Competition?>?>()
    val updateCompetitionResponse = _updateCompetitionResponse

    private val _updateCompetitionDayResponse = MutableLiveData<ApiResponse<CompetitionDay?>?>()
    val updateCompetitionDayResponse = _updateCompetitionDayResponse

    private val _deleteCompetitionResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteCompetitionResponse = _deleteCompetitionResponse

    private val _getCompetitionDayResponse = MutableLiveData<ApiResponse<CompetitionDay?>?>()
    val getCompetitionDayResponse = _getCompetitionDayResponse

    fun getCompetitionDay(competitionId: UUID, day: LocalDate) = baseRequest(
        _getCompetitionDayResponse
    ) {
        competitionRepository.getCompetitionDay(
            competitionId = competitionId,
            day = day,
        )
    }

    fun clearUpdateResponse() {
        _updateCompetitionResponse.postValue(null)
    }

    fun clearUpdateDayResponse() {
        _updateCompetitionDayResponse.postValue(null)
    }

    fun clearDeleteResponse() {
        _deleteCompetitionResponse.postValue(null)
    }

    fun clearCreateResponse() {
        _competitionsAddResponse.postValue(null)
    }

    fun createCompetition(competition: CreateCompetitionRequest) = baseRequest(
        _competitionsAddResponse
    ) {
        competitionRepository.createCompetition(competition)
    }

    fun updateCompetitionDay(competitionDay: CompetitionDayUpdateRequest, competitionId: UUID) = baseRequest(
        _updateCompetitionDayResponse
    ) {
        competitionRepository.updateCompetitionDay(competitionDay, competitionId)
    }

    fun updateCompetition(competition: CreateCompetitionRequest, id: UUID) = baseRequest(
        _updateCompetitionResponse
    ) {
        competitionRepository.updateCompetition(competition, id)
    }

    fun deleteCompetition(id: UUID) = baseRequest(
        _deleteCompetitionResponse
    ) {
        competitionRepository.deleteCompetition(id)
    }

    fun getCompetition(id: UUID) = baseRequest(
        _getCompetitionInfoResponse
    ) {
        competitionRepository.getCompetition(id)
    }

    fun getCompetitions(index: Int) {
        when (index) {
            0 -> {
                if (uiState.value.lastFetched == null || uiState.value.lastFetched != LocalDate.now()) {
                    uiState.value.lastFetched = LocalDate.now()
                    baseRequest(
                        _getCompetitionsPrevResponse
                    ) {
                        competitionRepository.getCompetitions(
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
                        _getCompetitionsCurrentResponse
                    ) {
                        competitionRepository.getCompetitions(
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
                        _getCompetitionsNextResponse
                    ) {
                        competitionRepository.getCompetitions(
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

    fun setSelectedCompetition(competition: Competition) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCompetition = competition
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