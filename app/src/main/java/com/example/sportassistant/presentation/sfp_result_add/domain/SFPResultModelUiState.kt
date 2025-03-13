package com.example.sportassistant.presentation.sfp_result_add.domain

import java.time.LocalDate
import java.util.UUID

data class SFPResultModelUiState(
    val categoryId: UUID? = null,
    val date: LocalDate? = null,
    val result: String = "",
    val goals: String = "",
    val notes: String = "",
)
