package com.example.sportassistant.data.schemas

data class ErrorDetail(
    val message: String
)

data class ErrorResponse(
    val detail: ErrorDetail
)

