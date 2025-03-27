package com.example.sportassistant.presentation.registration.domain.model

import com.example.sportassistant.domain.model.Coach
import java.util.UUID

data class ProfileInfoUiState(
    val sportType: String = "",
    val qualification: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val gender: String = "",

    val selectedCoach: Coach? = null,
)