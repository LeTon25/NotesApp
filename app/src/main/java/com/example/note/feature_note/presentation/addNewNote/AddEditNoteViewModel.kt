package com.example.note.feature_note.presentation.addNewNote

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.feature_note.domain.model.InvalidNoteException
import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private  val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private var currentNoteId: Int? = null

    private var _noteTitle = mutableStateOf(NoteTextFieldState(
        hint= "Nhập tiêu đề"
    ))
    val noteTitle :State<NoteTextFieldState> get() = _noteTitle

    private var _noteContent = mutableStateOf(NoteTextFieldState(
        hint= "Nhập nội dung"
    ))

    private var _noteImage  = mutableStateOf(NoteImageFieldState())
    val noteImage :State<NoteImageFieldState> get() =  _noteImage
    val noteContent : State<NoteTextFieldState> get() = _noteContent

    private  var _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val noteColor:State<Int> get() = _noteColor

    private  var _eventFlow = MutableSharedFlow<UiEvent>()
        val eventFlow get() = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if ( noteId != -1 ){
                viewModelScope.launch {
                            noteUseCases.getNodeById(noteId)?.also {
                                note ->
                                currentNoteId = note.id
                                _noteTitle.value = _noteTitle.value.copy(
                                    text = note.title,
                                    isHintVisible = false
                                )
                                _noteContent.value = _noteContent.value.copy(
                                    text = note.content,
                                    isHintVisible = false
                                )
                                _noteImage.value = _noteImage.value.copy(
                                    listImageUri = note.images
                                )
                                _noteColor.value = note.color
                            }
                    }
                }
            }

        }
    fun  onEvent(event: AddEditNoteEvent){
        when (event) {
            is AddEditNoteEvent.EnteredTitle ->{
                _noteTitle.value = _noteTitle.value.copy(
                    text =  event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && _noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent ->{
                _noteContent.value = _noteContent.value.copy(
                    text =  event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && _noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor ->{
                _noteColor.value = event.value
            }
            is AddEditNoteEvent.SaveNote ->{
                viewModelScope.launch {
                    try {
                        if (currentNoteId != null){
                            noteUseCases.addNode(
                                Note(
                                    title = _noteTitle.value.text,
                                    content = _noteContent.value.text,
                                    timestamp = System.currentTimeMillis() ,
                                    color = _noteColor.value,
                                    id = currentNoteId,
                                    images =  _noteImage.value.listImageUri
                                )
                            )
                        }else{
                            noteUseCases.addNode(
                                Note(
                                    title = _noteTitle.value.text,
                                    content = _noteContent.value.text,
                                    timestamp = System.currentTimeMillis() ,
                                    color = _noteColor.value,
                                    images =  _noteImage.value.listImageUri
                                )
                            )
                        }
                        _eventFlow.emit(UiEvent.saveNote)
                    }catch (e :InvalidNoteException){
                        _eventFlow.emit(UiEvent.showSnackBar(
                            message = e.message ?:"Failed to save note"
                        ))
                    }
                }
            }
            is AddEditNoteEvent.AddImage ->{
                _noteImage.value = _noteImage.value.copy(listImageUri = event.listUri + _noteImage.value.listImageUri)
            }
            is AddEditNoteEvent.AddTimeToNoti -> {}
        }
    }
}

