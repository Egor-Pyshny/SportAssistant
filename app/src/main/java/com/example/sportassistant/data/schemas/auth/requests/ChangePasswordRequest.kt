package com.example.sportassistant.data.schemas.auth.requests

data class ChangePasswordRequest(
    val email: String,
    val password: String,
)
