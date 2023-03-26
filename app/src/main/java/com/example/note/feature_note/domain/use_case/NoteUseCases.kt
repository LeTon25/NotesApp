package com.example.note.feature_note.domain.use_case

class NoteUseCases(val getNotes :GetNotes,
                   val deleteNote:DeleteNote ,
                   val addNode:AddNote,
                   val getNodeById:GetNoteById
                   ) {

}