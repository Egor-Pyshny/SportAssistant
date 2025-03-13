package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.training_camp_day.requests.TrainingCampDayUpdateRequest
import com.example.sportassistant.data.schemas.training_camps.requests.CreateTrainingCampRequest
import com.example.sportassistant.domain.enums.CompetitionStatus
import com.example.sportassistant.domain.interfaces.services.TrainingCampsApiService
import com.example.sportassistant.presentation.utils.apiRequestFlow
import java.time.LocalDate
import java.util.UUID

class TrainingCampsRepository(
    private val trainingCampsService: TrainingCampsApiService
) {
    fun getTrainingCamps(
        date: LocalDate,
        status: CompetitionStatus,
    ) = apiRequestFlow {
        trainingCampsService.getTrainingCamps(
            date = date,
            status = status.name,
        )
    }

    fun updateTrainingCampDay(
        campDay: TrainingCampDayUpdateRequest,
        campId: UUID
    ) = apiRequestFlow {
        trainingCampsService.updateTrainingCampDay(
            body = campDay,
            campId = campId,
        )
    }

    fun getTrainingCampDay(
        campId: UUID,
        day: LocalDate,
    ) = apiRequestFlow {
        trainingCampsService.getTrainingCampDay(
            campId = campId,
            day = day,
        )
    }

    fun updateTrainingCamps(
        camp: CreateTrainingCampRequest,
        id: UUID,
    ) = apiRequestFlow {
        trainingCampsService.updateTrainingCamps(
            body = camp,
            campId = id,
        )
    }

    fun getTrainingCamp(id: UUID) = apiRequestFlow {
        trainingCampsService.getTrainingCamp(id)
    }

    fun deleteTrainingCamp(id: UUID) = apiRequestFlow {
        trainingCampsService.deleteTrainingCamp(id)
    }

    fun createTrainingCamp(data: CreateTrainingCampRequest) = apiRequestFlow {
        trainingCampsService.createTrainingCamp(data)
    }
}