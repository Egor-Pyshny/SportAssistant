package com.example.sportassistant.data.schemas.notes.requests

import java.time.LocalDate

data class NoteCreateRequest(
    val date: LocalDate,
    val text: String,
)
