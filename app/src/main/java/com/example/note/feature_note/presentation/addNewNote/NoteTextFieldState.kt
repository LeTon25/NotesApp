package com.example.note.feature_note.presentation.addNewNote

data class NoteTextFieldState (
    val text : String = "",
    val hint :String ="",
    val isHintVisible:Boolean = true
        )