package com.example.sportassistant.presentation.sfp_graphic.domain

import java.time.LocalDate
import java.util.UUID

data class SFPResultsGraphicUiState(
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val datePeriod: String = "",
    val selectedCategoryId: UUID? = null,
    val category: String = "",
)
