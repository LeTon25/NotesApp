package com.example.note.feature_note.domain.use_case

import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.repository.NoteRepository
import com.example.note.feature_note.domain.util.NoteOrder
import com.example.note.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes (
    private  val noteRepository: NoteRepository
        ) {
    operator  fun invoke(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)):Flow<List<Note>>{
        return noteRepository.getAllNotes().map { notes ->
             when(noteOrder.orderType){
                 is OrderType.Descending ->{
                     when(noteOrder) {
                         is NoteOrder.Title ->{
                             notes.sortedByDescending { it.title }
                         }
                         is NoteOrder.Date ->{
                                notes.sortedByDescending { it.timestamp }
                         }
                         is NoteOrder.Color -> {
                                notes.sortedByDescending { it.color }
                         }
                     }
                 }
                 is OrderType.Ascending -> {
                     when(noteOrder) {
                         is NoteOrder.Title ->{
                             notes.sortedBy { it.title }
                         }
                         is NoteOrder.Date ->{
                             notes.sortedBy { it.timestamp }
                         }
                         is NoteOrder.Color -> {
                             notes.sortedBy { it.color }
                         }
                     }
                 }
             }
        }
    }
}