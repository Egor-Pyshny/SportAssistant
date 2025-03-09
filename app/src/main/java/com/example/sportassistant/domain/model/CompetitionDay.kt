package com.example.sportassistant.domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.UUID

data class CompetitionDay(
    val id: UUID?,
    val date: LocalDate,
    val results: String,
    val notes: String,
)
