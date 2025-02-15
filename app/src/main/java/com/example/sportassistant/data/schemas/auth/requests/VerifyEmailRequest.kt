package com.example.sportassistant.data.schemas.auth.requests

data class VerifyEmailRequest(
    val email: String,
    val code: String = "111111",
)
