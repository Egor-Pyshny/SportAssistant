package com.example.sportassistant.presentation.reset_password.domain

data class ResetPasswordUiState(
    val email: String = "",
    val code: String = "",
    val password: String = "",
    val repeatPassword: String = "",

    val userMailError: Boolean = false,
    val passwordVisibility: Boolean = false,
    val repeatPasswordVisibility: Boolean = false,
)
