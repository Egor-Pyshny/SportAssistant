package com.example.sportassistant.presentation.notes.domain

import com.example.sportassistant.domain.model.AnthropometricParams
import com.example.sportassistant.domain.model.Note

data class NotesUiState(
    val selectedNote: Note? = null,

    var shouldRefetch: Boolean = true,
)
