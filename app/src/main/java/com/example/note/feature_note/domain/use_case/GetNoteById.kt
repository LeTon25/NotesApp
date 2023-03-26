package com.example.note.feature_note.domain.use_case

import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.repository.NoteRepository

class GetNoteById (private  val noteRepository: NoteRepository) {
    suspend operator fun invoke(id : Int) : Note?{
        return noteRepository.getNodeById(id)
    }
}