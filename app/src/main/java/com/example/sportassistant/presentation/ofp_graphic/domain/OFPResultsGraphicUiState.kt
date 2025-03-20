package com.example.sportassistant.presentation.ofp_graphic.domain

import java.time.LocalDate
import java.util.UUID

data class OFPResultsGraphicUiState(
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val datePeriod: String = "",
    val selectedCategoryId: UUID? = null,
    val category: String = "",
)
