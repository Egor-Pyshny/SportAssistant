package com.example.sportassistant.presentation.notes.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.NotesRepository
import com.example.sportassistant.data.schemas.notes.requests.NoteCreateRequest
import com.example.sportassistant.domain.model.Note
import com.example.sportassistant.presentation.notes.domain.NotesUiState
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class NotesViewModel(
    private val notesRepository: NotesRepository,
): BaseViewModel() {
    private val _uiState = MutableStateFlow<NotesUiState>(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val _noteAddResponse = MutableLiveData<ApiResponse<Void?>?>()
    val noteAddResponse = _noteAddResponse

    private val _getNotesResponse = MutableLiveData<ApiResponse<List<Note>?>>()
    val getNotesResponse = _getNotesResponse

    private val _getNoteInfoResponse = MutableLiveData<ApiResponse<Note?>>()
    val getNoteInfoResponse = _getNoteInfoResponse

    private val _updateNoteResponse = MutableLiveData<ApiResponse<Void?>?>()
    val updateNoteResponse = _updateNoteResponse

    private val _deleteNoteResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteNoteResponse = _deleteNoteResponse

    fun deleteNote(paramsId: UUID) = baseRequest(
        _deleteNoteResponse
    ) {
        notesRepository.deleteNote(
            id = paramsId,
        )
    }

    fun clearCreateResponse() {
        _noteAddResponse.postValue(null)
    }

    fun clearUpdateResponse() {
        _updateNoteResponse.postValue(null)
    }

    fun clearDeleteResponse() {
        _deleteNoteResponse.postValue(null)
    }

    fun updateNote(data: NoteCreateRequest, paramsId: UUID) = baseRequest(
        _updateNoteResponse
    ) {
        notesRepository.updateNote(
            data = data,
            id = paramsId,
        )
    }

    fun addNote(data: NoteCreateRequest) = baseRequest(
        _noteAddResponse
    ) {
        notesRepository.createNote(
            data = data
        )
    }

    fun getNoteInfo(id: UUID) = baseRequest(
        _getNoteInfoResponse
    ) {
        notesRepository.getNoteInfo(
            id = id
        )
    }

    fun getNotes() {
        if (uiState.value.shouldRefetch) {
            uiState.value.shouldRefetch = false
            baseRequest(
                _getNotesResponse
            ) {
                notesRepository.getNotes()
            }
        }
    }

    fun setSelectedNote(note: Note) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedNote = note
            )
        }
    }

    fun setShouldRefetch(flag: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                shouldRefetch = flag
            )
        }
    }

}