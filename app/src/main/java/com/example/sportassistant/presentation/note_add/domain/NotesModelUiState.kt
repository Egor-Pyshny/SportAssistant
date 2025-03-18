package com.example.sportassistant.presentation.note_add.domain

import java.time.LocalDate

data class NotesModelUiState(
    val date: LocalDate? = null,
    val text: String = "",
)
