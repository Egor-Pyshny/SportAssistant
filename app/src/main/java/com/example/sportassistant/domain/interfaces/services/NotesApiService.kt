package com.example.sportassistant.domain.interfaces.services

import com.example.sportassistant.data.schemas.notes.requests.NoteCreateRequest
import com.example.sportassistant.domain.model.Note
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface NotesApiService {

    @GET("notes/")
    suspend fun getNotes(): Response<List<Note>>

    @POST("notes/create")
    suspend fun createNote(
        @Body body: NoteCreateRequest,
    ): Response<Void>

    @DELETE("notes/delete/{note_id}")
    suspend fun deleteNote(
        @Path("note_id") noteId: UUID,
    ): Response<Void>

    @POST("notes/update/{note_id}")
    suspend fun updateNote(
        @Body body: NoteCreateRequest,
        @Path("note_id") noteId: UUID,
    ): Response<Void>

    @GET("notes/get/{note_id}")
    suspend fun getNoteInfo(
        @Path("note_id") noteId: UUID,
    ): Response<Note>
}