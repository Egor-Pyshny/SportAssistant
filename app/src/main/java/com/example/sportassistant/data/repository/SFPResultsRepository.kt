package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.sfp_results.requests.SFPResultsCreateRequest
import com.example.sportassistant.domain.interfaces.services.SFPResultsApiService
import com.example.sportassistant.presentation.utils.apiRequestFlow
import java.time.LocalDate
import java.util.UUID

class SFPResultsRepository (
    private val sfpResultsService: SFPResultsApiService
) {
    fun getCategories() = apiRequestFlow {
        sfpResultsService.getSFPCategories()
    }

    fun getSFPResults() = apiRequestFlow {
        sfpResultsService.getSFPResults()
    }

    fun createSFPResult(
        data: SFPResultsCreateRequest
    ) = apiRequestFlow {
        sfpResultsService.createSFPResult(
            body = data,
        )
    }

    fun updateSFPResult(
        data: SFPResultsCreateRequest,
        id: UUID,
    ) = apiRequestFlow {
        sfpResultsService.updateSFPResult(
            body = data,
            sfpId = id,
        )
    }

    fun deleteSFPResult(id: UUID) = apiRequestFlow {
        sfpResultsService.deleteSFPResult(id)
    }

    fun getSFPResultInfo(id: UUID) = apiRequestFlow {
        sfpResultsService.getSFPResultInfo(id)
    }

    fun getGraphicData(
        startDate: LocalDate,
        endDate: LocalDate,
        categoryId: UUID
    ) = apiRequestFlow {
        sfpResultsService.getGraphicData(
            startDate = startDate,
            endDate = endDate,
            categoryId = categoryId,
        )
    }
}