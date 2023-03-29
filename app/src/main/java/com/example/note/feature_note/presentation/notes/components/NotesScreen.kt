@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.note.feature_note.presentation.notes.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.note.feature_note.presentation.notes.NotesEvent
import com.example.note.feature_note.presentation.notes.NotesViewModel
import com.example.note.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
     navController: NavController,
     notesViewModel: NotesViewModel = hiltViewModel()
){
        val state = notesViewModel.notesState.value
        val snackbarHostState = remember{ SnackbarHostState()}
        val coroutineScope = rememberCoroutineScope()

       Scaffold(
           snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
           floatingActionButton = {
               FloatingActionButton(onClick = {navController.navigate(Screen.AddEditNoteScreen.route) },
                                  containerColor = MaterialTheme.colorScheme.primary
                   ) {
                   Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
               }
           }
       ){ padding ->
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                ) {
                   Row(modifier =  Modifier.fillMaxWidth(),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.SpaceBetween
                   ){
                        Text(text = "Your Note",
                             style =  MaterialTheme.typography.labelMedium
                            )

                        IconButton(onClick = { notesViewModel.onEvent(NotesEvent.ToggleOrderSection) }) {
                            Icon(imageVector = Icons.Default.Sort , contentDescription ="Sort" )
                        }
                   }
                    AnimatedVisibility(visible = state.isOrderSectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically(),
                    ) {
                        OrderSection(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onChangeOrder = { notesViewModel.onEvent(NotesEvent.Order(it)) },
                            noteOrder = state.noteOrder,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(modifier =  Modifier.fillMaxSize()){
                       items(state.notes){
                           note ->
                            NoteItem(note = note,
                                onDeleteClick = {
                                    notesViewModel.onEvent(NotesEvent.DeleteNote(note))
                                    coroutineScope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "Deleted Note",
                                            actionLabel = "Undo"
                                        )
                                        if (result == SnackbarResult.ActionPerformed){
                                            notesViewModel.onEvent(NotesEvent.RestoreNote)
                                        }
                                    }

                                },
                                modifier = Modifier.fillMaxWidth()
                                    .clickable {
                                        navController.navigate(Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}")
                                    }
                                    .padding(top = 4.dp)
                            )
                       }
                    }
                }
       }
}