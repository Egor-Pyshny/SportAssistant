package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.comprehensive_examination.requests.ComprehensiveExaminationCreateRequest
import com.example.sportassistant.data.schemas.med_examination.requests.MedExaminationCreateRequest
import com.example.sportassistant.domain.interfaces.services.ComprehensiveExaminationApiService
import com.example.sportassistant.domain.interfaces.services.MedExaminationApiService
import com.example.sportassistant.presentation.utils.apiRequestFlow
import java.util.UUID

class MedExaminationRepository(
    private val medExaminationService: MedExaminationApiService
) {
    fun getMedExaminations() = apiRequestFlow {
        medExaminationService.getMedExaminations()
    }

    fun createMedExamination(
        data: MedExaminationCreateRequest
    ) = apiRequestFlow {
        medExaminationService.createMedExamination(
            body = data,
        )
    }

    fun updateMedExamination(
        data: MedExaminationCreateRequest,
        id: UUID,
    ) = apiRequestFlow {
        medExaminationService.updateMedExamination(
            body = data,
            examId = id,
        )
    }

    fun deleteMedExamination(id: UUID) = apiRequestFlow {
        medExaminationService.deleteMedExamination(id)
    }

    fun getMedExaminationInfo(id: UUID) = apiRequestFlow {
        medExaminationService.getMedExaminationInfo(id)
    }
}