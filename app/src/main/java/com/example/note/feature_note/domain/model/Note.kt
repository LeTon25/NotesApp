package com.example.note.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.note.ui.theme.Pink40
import com.example.note.ui.theme.Pink80
import com.example.note.ui.theme.Purple40
import com.example.note.ui.theme.Purple80
import com.example.note.ui.theme.PurpleGrey80

@Entity
data  class Note (
    val title :String,
    val content : String,
    val timestamp : Long,
    val color :Int,
    val images : List<String>,
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
        ){

    companion object{
        val noteColors = listOf(Purple80, Purple40, Pink80, PurpleGrey80, Pink40)
    }
}
class InvalidNoteException(message:String) : Exception(message)