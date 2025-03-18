package com.example.sportassistant.presentation.comprehensive_examination_add.domain

import java.time.LocalDate

data class ComprehensiveExaminationUiState(
    val date: LocalDate? = null,
    val institution: String = "",
    val specialists: String = "",
    val methods: String = "",
    val recommendations: String = "",
)
