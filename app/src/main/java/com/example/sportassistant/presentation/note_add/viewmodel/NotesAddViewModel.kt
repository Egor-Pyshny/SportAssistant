package com.example.sportassistant.presentation.note_add.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sportassistant.presentation.note_add.domain.NotesModelUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class NotesAddViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<NotesModelUiState>(NotesModelUiState())
    val uiState: StateFlow<NotesModelUiState> = _uiState.asStateFlow()

    fun setDate(date: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(
                date = date
            )
        }
    }

    fun setText(text: String) {
        _uiState.update { currentState ->
            currentState.copy(
                text = text
            )
        }
    }
}