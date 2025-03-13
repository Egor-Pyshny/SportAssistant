package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.ant_params.requests.AnthropometricParamsCreateRequest
import com.example.sportassistant.data.schemas.ofp_results.requests.OFPResultsCreateRequest
import com.example.sportassistant.domain.interfaces.services.AnthropometricParamsService
import com.example.sportassistant.domain.interfaces.services.OFPResultsService
import com.example.sportassistant.presentation.utils.apiRequestFlow
import java.util.UUID

class AnthropometricParamsRepository(
    private val anthropometricParamsResultsService: AnthropometricParamsService
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