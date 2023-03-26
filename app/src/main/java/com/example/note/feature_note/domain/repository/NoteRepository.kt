package com.example.note.feature_note.domain.repository

import com.example.note.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes() : Flow<List<Note>>

    suspend fun getNodeById(id :Int) : Note?

    suspend fun  addNote(note: Note)

    suspend fun deleteNode(note: Note)
}