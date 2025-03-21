package com.example.sportassistant.presentation.note_info.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportassistant.data.repository.NotesRepository
import com.example.sportassistant.data.schemas.notes.requests.NoteCreateRequest
import com.example.sportassistant.domain.model.Note
import com.example.sportassistant.presentation.note_add.domain.NotesModelUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

class NotesInfoViewModel(
    private val notesRepository: NotesRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<NotesModelUiState>(NotesModelUiState())
    val uiState: StateFlow<NotesModelUiState> = _uiState.asStateFlow()

    private val _getNoteInfoResponse = MutableLiveData<ApiResponse<Note?>>()
    val getNoteInfoResponse = _getNoteInfoResponse

    private val _updateNoteResponse = MutableLiveData<ApiResponse<Void?>?>()
    val updateNoteResponse = _updateNoteResponse

    fun updateNote(data: NoteCreateRequest, paramsId: UUID) = baseRequest(
        _updateNoteResponse
    ) {
        notesRepository.updateNote(
            data = data,
            id = paramsId,
        )
    }

    fun getNoteInfo(id: UUID) = baseRequest(
        _getNoteInfoResponse
    ) {
        notesRepository.getNoteInfo(
            id = id
        )
    }

    fun clearUpdateResponse() {
        _updateNoteResponse.postValue(null)
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