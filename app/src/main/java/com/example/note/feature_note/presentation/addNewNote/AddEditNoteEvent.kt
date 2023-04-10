package com.example.note.feature_note.presentation.addNewNote

import android.net.Uri
import androidx.compose.ui.focus.FocusState
import com.example.note.feature_note.alarm.AlarmSheduler

sealed class AddEditNoteEvent {
    data class EnteredTitle(val value:String ) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState):AddEditNoteEvent()
    data class EnteredContent(val value: String): AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class ChangeColor(val value :Int):AddEditNoteEvent()
    data class AddImage(val listUri : List<String>) : AddEditNoteEvent()
    data class SetTimeForNoti(val timeString : String):AddEditNoteEvent()
    data class SendToAlarmManager(val alarmSheduler: AlarmSheduler): AddEditNoteEvent()
    object RemoveTimeForNoti:AddEditNoteEvent()
    object SaveNote : AddEditNoteEvent()

}
