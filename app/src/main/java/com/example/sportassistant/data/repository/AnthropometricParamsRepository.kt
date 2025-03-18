package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.ant_params.requests.AnthropometricParamsCreateRequest
import com.example.sportassistant.domain.enums.AnthropometricParamsMeasures
import com.example.sportassistant.domain.interfaces.services.AnthropometricParamsApiService
import com.example.sportassistant.presentation.utils.apiRequestFlow
import java.time.LocalDate
import java.util.UUID

class AnthropometricParamsRepository(
    private val anthropometricParamsResultsService: AnthropometricParamsApiService
) {
    fun getAnthropometricParams() = apiRequestFlow {
        anthropometricParamsResultsService.getAnthropometricParams()
    }

    fun createAnthropometricParams(
        data: AnthropometricParamsCreateRequest
    ) = apiRequestFlow {
        anthropometricParamsResultsService.createAnthropometricParams(
            body = data,
        )
    }

    fun getGraphicData(
        startDate: LocalDate,
        endDate: LocalDate,
        category: AnthropometricParamsMeasures
    ) = apiRequestFlow {
        anthropometricParamsResultsService.getGraphicData(
            startDate = startDate,
            endDate = endDate,
            category = category,
        )
    }

    fun updateAnthropometricParams(
        data: AnthropometricParamsCreateRequest,
        id: UUID,
    ) = apiRequestFlow {
        anthropometricParamsResultsService.updateAnthropometricParams(
            body = data,
            paramsId = id,
        )
    }

    fun deleteAnthropometricParams(id: UUID) = apiRequestFlow {
        anthropometricParamsResultsService.deleteAnthropometricParams(id)
    }

    fun getAnthropometricParamsInfo(id: UUID) = apiRequestFlow {
        anthropometricParamsResultsService.getAnthropometricParamsInfo(id)
    }
}