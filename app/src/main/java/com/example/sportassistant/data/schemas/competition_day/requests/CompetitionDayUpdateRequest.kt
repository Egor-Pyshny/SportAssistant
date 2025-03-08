package com.example.sportassistant.data.schemas.competition_day.requests

import java.time.LocalDate
import java.util.UUID

data class CompetitionDayUpdateRequest(
    val id: UUID?,
    val date: LocalDate,
    val result: String,
    val notes: String,
)
