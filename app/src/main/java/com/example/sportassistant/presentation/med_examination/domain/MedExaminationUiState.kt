package com.example.sportassistant.presentation.med_examination.domain

import com.example.sportassistant.domain.model.AnthropometricParams
import com.example.sportassistant.domain.model.MedExamination

data class MedExaminationUiState(
    val selectedMedExamination: MedExamination? = null,

    var shouldRefetch: Boolean = true,
)