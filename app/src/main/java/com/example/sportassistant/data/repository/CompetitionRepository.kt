package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.competition.requests.CreateCompetitionRequest
import com.example.sportassistant.data.schemas.competition_day.requests.CompetitionDayUpdateRequest
import com.example.sportassistant.data.schemas.competition_result.requests.CompetitionResultUpdateRequest
import com.example.sportassistant.domain.enums.CompetitionStatus
import com.example.sportassistant.domain.interfaces.services.CoachApiService
import com.example.sportassistant.domain.interfaces.services.CompetitionApiService
import com.example.sportassistant.domain.model.Competition
import com.example.sportassistant.presentation.utils.apiRequestFlow
import java.time.LocalDate
import java.util.UUID

class CompetitionRepository (
    private val competitionService: CompetitionApiService
) {
    fun getCompetitions(
        date: LocalDate,
        status: CompetitionStatus,
    ) = apiRequestFlow {
        competitionService.getCompetitions(
            date = date,
            status = status.name,
        )
    }

    fun updateCompetitionDay(
        competitionDay: CompetitionDayUpdateRequest, competitionId: UUID
    ) = apiRequestFlow {
        competitionService.updateCompetitionDay(
            body = competitionDay,
            competitionId = competitionId,
        )
    }

    fun getCompetitionResult(competitionId: UUID) = apiRequestFlow {
        competitionService.getCompetitionResult(
            competitionId = competitionId,
        )
    }

    fun updateCompetitionResult(
        competitionResult: CompetitionResultUpdateRequest, competitionId: UUID
    ) = apiRequestFlow {
        competitionService.updateCompetitionResult(
            body = competitionResult,
            competitionId = competitionId,
        )
    }

    fun getCompetitionDay(
        competitionId: UUID,
        day: LocalDate,
    ) = apiRequestFlow {
        competitionService.getCompetitionDay(
            competitionId = competitionId,
            day = day,
        )
    }

    fun updateCompetition(
        competition: CreateCompetitionRequest,
        id: UUID,
    ) = apiRequestFlow {
        competitionService.updateCompetitions(
            body = competition,
            competitionId = id,
        )
    }

    fun getCompetition(id: UUID) = apiRequestFlow {
        competitionService.getCompetition(id)
    }

    fun deleteCompetition(id: UUID) = apiRequestFlow {
        competitionService.deleteCompetition(id)
    }

    fun createCompetition(data: CreateCompetitionRequest) = apiRequestFlow {
        competitionService.createCompetition(data)
    }

    fun getCompetitionDays(id: UUID) = apiRequestFlow {
        competitionService.getCompetitionDays(id)
    }
}