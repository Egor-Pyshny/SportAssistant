package com.example.sportassistant.presentation.profile.domain.model

import com.example.sportassistant.domain.model.Coach

data class ProfileUiState(
    val selectedCoach: Coach? = null
)
