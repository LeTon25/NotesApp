package com.example.note.feature_note.presentation.addNewNote

sealed class UiEvent{
    data class showSnackBar(val message:String) : UiEvent()
    object saveNote : UiEvent()
}
