package com.example.sportassistant.data.schemas.competition_result.requests

import java.time.LocalDate
import java.util.UUID

data class CompetitionResultUpdateRequest(
    val id: UUID,
    val result: String,
    val notes: String,
)
