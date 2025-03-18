package com.example.sportassistant.presentation.med_examination_add.domain

import java.time.LocalDate

data class MedExaminationUiState(
    val date: LocalDate? = null,
    val institution: String = "",
    val methods: String = "",
    val recommendations: String = "",
)
