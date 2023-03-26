package com.example.note.feature_note.domain.use_case

import com.example.note.feature_note.domain.model.InvalidNoteException
import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNote(val repository: NoteRepository) {
    @Throws(InvalidNoteException::class)
    suspend operator fun  invoke(note: Note){
        if (note.title.isBlank())
        {
            throw InvalidNoteException("Tiêu đề không được để trống")
        }
        if (note.content.isBlank())
        {
            throw InvalidNoteException("Nội dung  không được để trống")
        }
        repository.addNote(note)
    }
}