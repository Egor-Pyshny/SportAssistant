package com.example.sportassistant.presentation.competition_calendar.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.CompetitionRepository
import com.example.sportassistant.domain.application_state.ApplicationState
import com.example.sportassistant.domain.enums.CompetitionStatus
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import java.time.LocalDate
import java.util.UUID

class CompetitionViewModel(
    private val competitionRepository: CompetitionRepository,
): BaseViewModel() {
    private val _getCompetitionsCurrentResponse = MutableLiveData<ApiResponse<List<Competition>?>>()
    val getCompetitionsCurrentResponse = _getCompetitionsCurrentResponse

    private val _getCompetitionsPrevResponse = MutableLiveData<ApiResponse<List<Competition>?>>()
    val getCompetitionsPrevResponse = _getCompetitionsPrevResponse

    private val _getCompetitionsNextResponse = MutableLiveData<ApiResponse<List<Competition>?>>()
    val getCompetitionsNextResponse = _getCompetitionsNextResponse

    private val _deleteCompetitionResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteCompetitionResponse = _deleteCompetitionResponse

    fun deleteCompetition(id: UUID) = baseRequest(
        _deleteCompetitionResponse
    ) {
        competitionRepository.deleteCompetition(id)
    }

    fun clearDelete() {
        _deleteCompetitionResponse.postValue(null)
    }

    fun getCompetitions(index: Int) {
        val state = ApplicationState.getState()
        when (index) {
            0 -> {
                baseRequest(
                    _getCompetitionsPrevResponse
                ) {
                    competitionRepository.getCompetitions(
                        date = LocalDate.now(),
                        status = CompetitionStatus.past
                    )
                }
            }
            1 -> {
                baseRequest(
                    _getCompetitionsCurrentResponse
                ) {
                    competitionRepository.getCompetitions(
                        date = LocalDate.now(),
                        status = CompetitionStatus.current
                    )
                }
            }
            2 -> {
                baseRequest(
                    _getCompetitionsNextResponse
                ) {
                    competitionRepository.getCompetitions(
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