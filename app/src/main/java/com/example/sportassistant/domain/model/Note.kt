package com.example.sportassistant.domain.model

import java.time.LocalDate
import java.util.UUID

data class Note(
    val id: UUID,
    val date: LocalDate,
    val text: String,
)
