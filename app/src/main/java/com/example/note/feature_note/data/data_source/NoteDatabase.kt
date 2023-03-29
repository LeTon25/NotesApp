package com.example.note.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.util.StringListConverter

@Database(entities = [Note::class] , version = 1)
@TypeConverters(StringListConverter::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDao :NoteDao
    companion object {
        val DATABASE_NAME = "note_db"
    }
}