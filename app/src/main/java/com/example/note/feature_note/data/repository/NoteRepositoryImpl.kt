package com.example.note.feature_note.data.repository

import com.example.note.feature_note.data.data_source.NoteDao
import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
     private val noteDao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun getNodeById(id: Int): Note? {
        return noteDao.getNodeById(id)
    }

    override suspend fun addNote(note: Note) {
         noteDao.addNote(note)
    }

    override suspend fun deleteNode(note: Note) {
        noteDao.deleteNode(note)
    }
}