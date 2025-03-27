package com.example.sportassistant.presentation.registration.domain.model

import java.util.UUID

data class RegistrationUiState(
    val userName: String = "",
    val userSurname: String = "",
    val userMail: String = "",
    val userPassword: String = "",
    val deviceId: UUID = UUID.randomUUID(),

    val userMailError: Boolean = false,
    val userPasswordError: Boolean = false,
    val passwordVisibility: Boolean = false,
)