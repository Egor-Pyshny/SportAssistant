package com.example.sportassistant.presentation.trainig_camps_add.domain

import java.time.LocalDate

data class TrainingCampUiState(
    val goals: String = "",
    val location: String = "",
    val notes: String = "",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
)