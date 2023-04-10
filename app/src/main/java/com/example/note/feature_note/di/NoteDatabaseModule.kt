package com.example.note.feature_note.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.note.feature_note.alarm.AlarmSchedulerImpl
import com.example.note.feature_note.alarm.AlarmSheduler
import com.example.note.feature_note.data.data_source.NoteDatabase
import com.example.note.feature_note.data.repository.NoteRepositoryImpl
import com.example.note.feature_note.domain.repository.NoteRepository
import com.example.note.feature_note.domain.use_case.AddNote
import com.example.note.feature_note.domain.use_case.DeleteNote
import com.example.note.feature_note.domain.use_case.GetNoteById
import com.example.note.feature_note.domain.use_case.GetNotes
import com.example.note.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteDatabaseModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app : Application): NoteDatabase{
             return  Room.databaseBuilder (
                 app,
                 NoteDatabase::class.java,
                 NoteDatabase.DATABASE_NAME
             ).build()
    }
    @Provides
    @Singleton
    fun provideNoteRepository(db:NoteDatabase) : NoteRepository{
        return  NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository):NoteUseCases{
        return  NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNode = AddNote(repository),
            getNodeById = GetNoteById(repository)
        )
    }
    @Provides
    @Singleton
    fun provideAlarmScheduler(@ApplicationContext context : Context):AlarmSheduler{
        return AlarmSchedulerImpl(context)
    }
}