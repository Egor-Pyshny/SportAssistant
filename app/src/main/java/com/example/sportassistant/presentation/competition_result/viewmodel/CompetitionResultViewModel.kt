package com.example.sportassistant.presentation.competition_result.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.CompetitionRepository
import com.example.sportassistant.data.schemas.competition_result.requests.CompetitionResultUpdateRequest
import com.example.sportassistant.domain.model.CompetitionResult
import com.example.sportassistant.presentation.competition_result.domain.CompetitionResultUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class CompetitionResultViewModel(
    private val competitionRepository: CompetitionRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<CompetitionResultUiState>(
        CompetitionResultUiState(
            notes = "",
            result = ""
        )
    )
    val uiState: StateFlow<CompetitionResultUiState> = _uiState.asStateFlow()

    private val _updateCompetitionResultResponse = MutableLiveData<ApiResponse<CompetitionResult?>?>()
    val updateCompetitionResultResponse = _updateCompetitionResultResponse

    private val _getCompetitionResultResponse = MutableLiveData<ApiResponse<CompetitionResult?>?>()
    val getCompetitionResultResponse = _getCompetitionResultResponse

    fun getCompetitionResult(competitionId: UUID) = baseRequest(
        _getCompetitionResultResponse
    ) {
        competitionRepository.getCompetitionResult(competitionId = competitionId)
    }

    fun updateCompetitionResult(
        competitionResult: CompetitionResultUpdateRequest,
        competitionId: UUID
    ) = baseRequest(
        _updateCompetitionResultResponse
    ) {
        competitionRepository.updateCompetitionResult(competitionResult, competitionId)
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
        _updateCompetitionResultResponse.postValue(null)
    }
}