package com.example.sportassistant.domain.model

import java.util.UUID

data class CompetitionResult(
    val id: UUID,
    val results: String,
    val notes: String,
)
