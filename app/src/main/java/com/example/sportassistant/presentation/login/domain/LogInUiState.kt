package com.example.sportassistant.presentation.login.domain

data class LogInUiState (
    val email: String = "",
    val password: String = "",

    val userMailError: Boolean = false,
    val passwordVisibility: Boolean = false,
)