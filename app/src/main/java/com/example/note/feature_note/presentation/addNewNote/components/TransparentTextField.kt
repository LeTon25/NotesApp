package com.example.note.feature_note.presentation.addNewNote.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
@Composable
fun TransparentTextField(
    text : String,
    hint :String,
    modifier: Modifier = Modifier,
    isHintVisible:Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    onFocusChange : (FocusState) ->Unit,
    singleLine :Boolean = false,
) {
    BasicTextField(value =if(isHintVisible) hint else text  ,
                   onValueChange =onValueChange,
                   textStyle= textStyle,
                    singleLine = singleLine,
                    modifier = modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            onFocusChange(it)
                        }
    )

}