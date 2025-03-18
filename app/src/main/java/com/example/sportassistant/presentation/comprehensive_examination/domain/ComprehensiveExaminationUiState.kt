package com.example.sportassistant.presentation.comprehensive_examination.domain

import com.example.sportassistant.domain.model.ComprehensiveExamination
import com.example.sportassistant.domain.model.MedExamination

data class ComprehensiveExaminationUiState(
    val selectedComprehensiveExamination: ComprehensiveExamination? = null,

    var shouldRefetch: Boolean = true,
)
