package com.example.sportassistant.data.repository

import com.example.sportassistant.data.schemas.notes.requests.NoteCreateRequest
import com.example.sportassistant.domain.interfaces.services.NotesApiService
import com.example.sportassistant.presentation.utils.apiRequestFlow
import java.util.UUID

class NotesRepository (
    private val notesService: NotesApiService
) {

    fun getNotes() = apiRequestFlow {
        notesService.getNotes()
    }

    fun createNote(
        data: NoteCreateRequest
    ) = apiRequestFlow {
        notesService.createNote(
            body = data,
        )
    }

    fun updateNote(
        data: NoteCreateRequest,
        id: UUID,
    ) = apiRequestFlow {
        notesService.updateNote(
            body = data,
            noteId = id,
        )
    }

    fun deleteNote(id: UUID) = apiRequestFlow {
        notesService.deleteNote(id)
    }

    fun getNoteInfo(id: UUID) = apiRequestFlow {
        notesService.getNoteInfo(id)
    }
}