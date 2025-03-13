package com.example.sportassistant.presentation.ofp_results.domain

import com.example.sportassistant.domain.model.OFPResult

data class OFPResultsUiState(
    val selectedOFP: OFPResult? = null,

    var shouldRefetch: Boolean = true,
)
