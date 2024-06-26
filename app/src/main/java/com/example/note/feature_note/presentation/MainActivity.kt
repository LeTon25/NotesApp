package com.example.note.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.note.feature_note.alarm.AlarmSchedulerImpl
import com.example.note.feature_note.alarm.AlarmSheduler
import com.example.note.feature_note.presentation.addNewNote.components.AddEditNoteScreen
import com.example.note.feature_note.presentation.notes.components.NotesScreen
import com.example.note.feature_note.presentation.util.Screen
import com.example.note.ui.theme.NoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController,
                            startDestination = Screen.NotesScreen.route
                        ){
                        composable(route = Screen.NotesScreen.route){
                            NotesScreen(navController = navController)
                        }
                        composable(route =  Screen.AddEditNoteScreen.route +
                         "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument("noteId" ){type = NavType.IntType
                                                            defaultValue = -1
                                                      },
                                navArgument("noteColor" ){type = NavType.IntType
                                    defaultValue = -1
                                },
                            ),

                        ){
                                    val alarmScheduler =  AlarmSchedulerImpl(this@MainActivity)
                                    val color = it.arguments?.getInt("noteColor") ?: 1
                                    AddEditNoteScreen(navController = navController, noteColor = color, alarmScheduler= alarmScheduler)
                        }
                    }
                }
            }
        }
    }
}
