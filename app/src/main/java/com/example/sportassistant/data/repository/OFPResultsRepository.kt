package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.ofp_results.requests.OFPResultsCreateRequest
import com.example.sportassistant.data.schemas.training_camp_day.requests.TrainingCampDayUpdateRequest
import com.example.sportassistant.data.schemas.training_camps.requests.CreateTrainingCampRequest
import com.example.sportassistant.domain.enums.CompetitionStatus
import com.example.sportassistant.domain.interfaces.services.CompetitionApiService
import com.example.sportassistant.domain.interfaces.services.OFPResultsService
import com.example.sportassistant.presentation.utils.apiRequestFlow
import java.time.LocalDate
import java.util.UUID

class OFPResultsRepository (
    private val ofpResultsService: OFPResultsService
) {
    fun getCategories() = apiRequestFlow {
        ofpResultsService.getOFPCategories()
    }

    fun getOFPResults() = apiRequestFlow {
        ofpResultsService.getOFPResults()
    }

    fun createOFPResult(
        data: OFPResultsCreateRequest
    ) = apiRequestFlow {
        ofpResultsService.createOFPResult(
            body = data,
        )
    }

    fun updateOFPResult(
        data: OFPResultsCreateRequest,
        id: UUID,
    ) = apiRequestFlow {
        ofpResultsService.updateOFPResult(
            body = data,
            ofpId = id,
        )
    }

    fun deleteOFPResult(id: UUID) = apiRequestFlow {
        ofpResultsService.deleteOFPResult(id)
    }

    fun getOFPResultInfo(id: UUID) = apiRequestFlow {
        ofpResultsService.getOFPResultInfo(id)
    }
}