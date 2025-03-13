package com.example.sportassistant.data.schemas.training_camp_day.requests

import java.time.LocalDate
import java.util.UUID

data class TrainingCampDayUpdateRequest(
    val id: UUID?,
    val date: LocalDate,
    val goals: String,
    val notes: String,
)
