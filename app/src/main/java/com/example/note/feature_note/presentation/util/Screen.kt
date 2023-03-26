package com.example.note.feature_note.presentation.util

sealed class Screen(val route:String){
    object NotesScreen : Screen("notes_screen")
    object  AddEditNoteScreen :Screen("addEditNote_screen")
}
