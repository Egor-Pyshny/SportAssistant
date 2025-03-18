package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.ant_params.requests.AnthropometricParamsCreateRequest
import com.example.sportassistant.data.schemas.comprehensive_examination.requests.ComprehensiveExaminationCreateRequest
import com.example.sportassistant.domain.enums.AnthropometricParamsMeasures
import com.example.sportassistant.domain.interfaces.services.AnthropometricParamsApiService
import com.example.sportassistant.domain.interfaces.services.ComprehensiveExaminationApiService
import com.example.sportassistant.presentation.utils.apiRequestFlow
import java.time.LocalDate
import java.util.UUID

class ComprehensiveExaminationRepository(
    private val comprehensiveExaminationService: ComprehensiveExaminationApiService
) {
    fun getComprehensiveExaminations() = apiRequestFlow {
        comprehensiveExaminationService.getComprehensiveExaminations()
    }

    fun createComprehensiveExamination(
        data: ComprehensiveExaminationCreateRequest
    ) = apiRequestFlow {
        comprehensiveExaminationService.createComprehensiveExamination(
            body = data,
        )
    }

    fun updateComprehensiveExamination(
        data: ComprehensiveExaminationCreateRequest,
        id: UUID,
    ) = apiRequestFlow {
        comprehensiveExaminationService.updateComprehensiveExamination(
            body = data,
            examId = id,
        )
    }

    fun deleteComprehensiveExamination(id: UUID) = apiRequestFlow {
        comprehensiveExaminationService.deleteComprehensiveExamination(id)
    }

    fun getComprehensiveExaminationInfo(id: UUID) = apiRequestFlow {
        comprehensiveExaminationService.getComprehensiveExaminationInfo(id)
    }
}