package com.example.sportassistant.presentation.competition_add.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.CompetitionRepository
import com.example.sportassistant.data.schemas.competition.requests.CreateCompetitionRequest
import com.example.sportassistant.presentation.competition_add.domain.CompetitionUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class CompetitionAddViewModel(
    private val competitionRepository: CompetitionRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<CompetitionUiState>(CompetitionUiState())
    val uiState: StateFlow<CompetitionUiState> = _uiState.asStateFlow()

    private val _competitionsAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val competitionsAddResponse = _competitionsAddResponse

    fun createCompetition(competition: CreateCompetitionRequest) = baseRequest(
        _competitionsAddResponse
    ) {
        competitionRepository.createCompetition(competition)
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