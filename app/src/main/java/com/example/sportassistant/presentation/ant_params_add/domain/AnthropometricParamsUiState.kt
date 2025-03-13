package com.example.sportassistant.presentation.ant_params_add.domain

import java.time.LocalDate

data class AnthropometricParamsUiState(
    val date: LocalDate? = null,
    val weight: String = "",
    val height: String = "",
    val chestCircumference: String = "",
)
