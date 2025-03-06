package com.example.sportassistant.presentation.competition_add.domain

import java.time.LocalDate

data class CompetitionUiState(
    val name: String = "",
    val location: String = "",
    val notes: String = "",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
)