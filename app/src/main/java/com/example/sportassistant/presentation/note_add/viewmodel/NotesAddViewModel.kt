package com.example.sportassistant.presentation.note_add.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportassistant.data.repository.NotesRepository
import com.example.sportassistant.data.schemas.notes.requests.NoteCreateRequest
import com.example.sportassistant.presentation.note_add.domain.NotesModelUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class NotesAddViewModel(
    private val notesRepository: NotesRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<NotesModelUiState>(NotesModelUiState())
    val uiState: StateFlow<NotesModelUiState> = _uiState.asStateFlow()

    private val _noteAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val noteAddResponse = _noteAddResponse

    fun addNote(data: NoteCreateRequest) = baseRequest(
        _noteAddResponse
    ) {
        notesRepository.createNote(
            data = data
        )
    }

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