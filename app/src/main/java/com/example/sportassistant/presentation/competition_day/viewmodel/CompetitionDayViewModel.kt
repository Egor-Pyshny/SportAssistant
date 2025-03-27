package com.example.sportassistant.presentation.competition_day.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.CompetitionRepository
import com.example.sportassistant.data.schemas.competition_day.requests.CompetitionDayUpdateRequest
import com.example.sportassistant.domain.model.CompetitionDay
import com.example.sportassistant.presentation.competition_day.domain.CompetitionDayUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class CompetitionDayViewModel(
    private val competitionRepository: CompetitionRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<CompetitionDayUiState>(CompetitionDayUiState(
        notes = "",
        result = ""
    ))
    val uiState: StateFlow<CompetitionDayUiState> = _uiState.asStateFlow()

    private val _getCompetitionDayResponse = MutableLiveData<ApiResponse<CompetitionDay?>?>()
    val getCompetitionDayResponse = _getCompetitionDayResponse

    private val _updateCompetitionDayResponse = MutableLiveData<ApiResponse<CompetitionDay?>?>()
    val updateCompetitionDayResponse = _updateCompetitionDayResponse

    fun updateCompetitionDay(competitionDay: CompetitionDayUpdateRequest, competitionId: UUID) = baseRequest(
        _updateCompetitionDayResponse
    ) {
        competitionRepository.updateCompetitionDay(competitionDay, competitionId)
    }

    fun getCompetitionDay(competitionId: UUID, day: LocalDate) = baseRequest(
        _getCompetitionDayResponse
    ) {
        competitionRepository.getCompetitionDay(
            competitionId = competitionId,
            day = day,
        )
    }

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

    fun clearUpdate() {
        _updateCompetitionDayResponse.postValue(null)
    }
}