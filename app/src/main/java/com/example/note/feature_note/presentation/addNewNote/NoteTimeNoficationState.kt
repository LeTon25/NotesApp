package com.example.note.feature_note.presentation.addNewNote

data class NoteTimeNoficationState(
                                     val isSetted : Boolean = false,
                                     val isSentToAlarm:Boolean= false,
                                     val timeString: String? = null
)