package com.example.sportassistant.presentation.ant_params_graphic.domain

import com.example.sportassistant.domain.enums.AnthropometricParamsMeasures
import java.time.LocalDate

data class AnthropometricParamsGraphicUiState(
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val datePeriod: String = "",
    val measureString: String = "",
    val measure: AnthropometricParamsMeasures? = null,
)
