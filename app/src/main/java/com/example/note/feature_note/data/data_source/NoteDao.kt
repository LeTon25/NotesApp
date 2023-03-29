package com.example.note.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.note.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAllNotes() : Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id = :id")
    suspend fun getNodeById(id :Int) : Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  addNote(note: Note)

    @Delete
    suspend fun deleteNode(note: Note)


}