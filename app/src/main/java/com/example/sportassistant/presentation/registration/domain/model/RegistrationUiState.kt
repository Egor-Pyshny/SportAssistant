package com.example.sportassistant.presentation.registration.domain.model

import com.example.sportassistant.domain.model.Coach
import java.util.UUID

data class RegistrationUiState(
    val userName: String = "",
    val userSurname: String = "",
    val userMail: String = "",
    val userPassword: String = "",

    val userMailError: Boolean = false,
    val userPasswordError: Boolean = false,
    val passwordVisibility: Boolean = false,

    val sportType: String = "",
    val qualification: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val gender: String = "",

    val selectedCoach: Coach? = null,
)