package com.example.sportassistant.presentation.ant_params.domain

import com.example.sportassistant.domain.model.AnthropometricParams


data class AnthropometricParamsUiState(
    val selectedAnthropometricParams: AnthropometricParams? = null,

    var shouldRefetch: Boolean = true,
)
