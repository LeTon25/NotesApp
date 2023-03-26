package com.example.note.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.use_case.NoteUseCases
import com.example.note.feature_note.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel(){
    private  var _notesState  = mutableStateOf(NotesState())
    val notesState :State<NotesState> get() = _notesState
    private  var recentlyDeletedNote : Note? = null
    private var getNotesJob : Job? = null
    fun onEvent(notesEvent: NotesEvent){
       when(notesEvent){
           is NotesEvent.Order ->{
                if (_notesState.value.noteOrder::class == notesEvent.noteOrder::class &&
                        _notesState.value.noteOrder.orderType == notesEvent.noteOrder.orderType){
                        return
                }
               getNotes(notesEvent.noteOrder)

           }
           is NotesEvent.RestoreNote ->{
                viewModelScope.launch {
                    noteUseCases.addNode(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
           }
           is NotesEvent.DeleteNote ->{
               viewModelScope.launch {
                   noteUseCases.deleteNote(notesEvent.note)
                   recentlyDeletedNote= notesEvent.note
               }

           }
           is NotesEvent.ToggleOrderSection ->{
                    _notesState.value = _notesState.value.copy(
                        isOrderSectionVisible = !_notesState.value.isOrderSectionVisible
                    )
           }
       }
   }
    private fun getNotes(orderType: NoteOrder){
            getNotesJob?.cancel()
            getNotesJob = noteUseCases.getNotes(orderType).onEach { notes ->
                _notesState.value = _notesState.value.copy(
                    notes= notes,
                    noteOrder = orderType
                )
            }
                .launchIn(viewModelScope)
    }
}