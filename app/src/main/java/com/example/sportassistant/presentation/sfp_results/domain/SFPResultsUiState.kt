package com.example.sportassistant.presentation.sfp_results.domain

import com.example.sportassistant.domain.model.SFPResult

data class SFPResultsUiState(
    val selectedSFP: SFPResult? = null,

    var shouldRefetch: Boolean = true,
)
