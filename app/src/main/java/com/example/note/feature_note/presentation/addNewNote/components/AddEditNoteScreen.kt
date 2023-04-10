package com.example.note.feature_note.presentation.addNewNote.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.note.R
import com.example.note.feature_note.alarm.AlarmSchedulerImpl
import com.example.note.feature_note.alarm.AlarmSheduler
import com.example.note.feature_note.domain.model.Note
import com.example.note.feature_note.presentation.addNewNote.AddEditNoteEvent
import com.example.note.feature_note.presentation.addNewNote.AddEditNoteViewModel
import com.example.note.feature_note.presentation.addNewNote.UiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor :Int = -1,
    alarmScheduler:AlarmSheduler,
    viewModel : AddEditNoteViewModel = hiltViewModel()
) {
    val context  = LocalContext.current
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val imageState = viewModel.noteImage.value
    val snackbarHostState = SnackbarHostState()
    val timeNotiState = viewModel.noteTimeNoti.value
    val pickImage =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents(),
            onResult = { uris ->
                viewModel.onEvent(AddEditNoteEvent.AddImage(uris.map { uri -> uri.toString() }))
            })

    var isExpaned by remember {
        mutableStateOf(false)
    }
    val pattern: String = "yyyy-MM-dd HH:mm"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val date =LocalDateTime.now()
    var timeString = ""
    val timeDialog =
        TimePickerDialog(LocalContext.current, { _, hourOfDay, minute ->
                    timeString +=" $hourOfDay:$minute"
                    viewModel.onEvent(AddEditNoteEvent.SetTimeForNoti(timeString))
                    isExpaned = false;
        }, date.hour, date.minute, true)
    val dateDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            if (timeString.trim().length != 0 )
            {
                timeString = ""
            }
            var monthString = if (month < 10 ) "0$month" else "$month";
            var dayOfMonthString =if (dayOfMonth < 10 ) "0$dayOfMonth" else "$dayOfMonth";

            timeString ="$year-$monthString-$dayOfMonthString"
            timeDialog.show()
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth,
    )

    val coroutineScope = rememberCoroutineScope()

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.showSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.saveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                                viewModel.onEvent(AddEditNoteEvent.SendToAlarmManager(alarmScheduler))
                                viewModel.onEvent(AddEditNoteEvent.SaveNote)
                          },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
            }
        },

        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,

            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                coroutineScope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                            .focusable(enabled = true)
                    ) {

                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start ,
                verticalAlignment =Alignment.CenterVertically
            ) {
                AddImageButton(
                    modifier = Modifier
                        .padding(end = 4.dp),
                    painterSource = R.drawable.ic_add_image,
                    onclicked = {
                        pickImage.launch("image/*")
                    }
                )
                AddImageButton(
                    modifier = Modifier
                        .padding(end = 4.dp),
                    painterSource = R.drawable.ic_noti,
                    onclicked = {

                        isExpaned = !isExpaned
                    }
                )
                DropdownMenu(
                    expanded = isExpaned, onDismissRequest = { isExpaned = false },
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Chọn thời gian thông báo") },
                        onClick = { dateDialog.show() })
                }

                 Box(
                    modifier = Modifier
                        .clickable {
                            viewModel.onEvent(AddEditNoteEvent.RemoveTimeForNoti)
                        }
                        .clip(RoundedCornerShape(percent = 50))
                        .background(Color.Gray)
                        .padding(horizontal = 4.dp),
                ) {
                    Text(
                        text = timeNotiState.timeString ?: "",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    )
                }



            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                TransparentTextField(
                    text = titleState.text,
                    hint = titleState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                    },
                    isHintVisible = titleState.isHintVisible,
                    textStyle = MaterialTheme.typography.titleMedium,
                    singleLine = true
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                TransparentTextField(text = contentState.text,
                    hint = contentState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                    }, onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                    },
                    singleLine = false,
                    isHintVisible = contentState.isHintVisible,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.height(400.dp)
                )
            }
            LazyRow(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()

            )
            {
                items(imageState.listImageUri) { uri ->
                    ImageField(
                        uri = uri,
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)

                    )
                }
            }
        }

    }
}
