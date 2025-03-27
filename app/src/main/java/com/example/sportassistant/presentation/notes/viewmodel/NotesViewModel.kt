package com.example.sportassistant.presentation.notes.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.sportassistant.data.repository.NotesRepository
import com.example.sportassistant.domain.model.Note
import com.example.sportassistant.presentation.utils.ApiResponse
import com.example.sportassistant.presentation.utils.BaseViewModel
import java.util.UUID

class NotesViewModel(
    private val notesRepository: NotesRepository,
): BaseViewModel() {
    private val _getNotesResponse = MutableLiveData<ApiResponse<List<Note>?>>()
    val getNotesResponse = _getNotesResponse

    private val _deleteNoteResponse = MutableLiveData<ApiResponse<Void?>?>()
    val deleteNoteResponse = _deleteNoteResponse

    fun deleteNote(paramsId: UUID) = baseRequest(
        _deleteNoteResponse
    ) {
        notesRepository.deleteNote(
            id = paramsId,
        )
    }

    fun clearDeleteResponse() {
        _deleteNoteResponse.postValue(null)
    }

    fun getNotes() {
        baseRequest(
            _getNotesResponse
        ) {
            notesRepository.getNotes()
        }
    }
}