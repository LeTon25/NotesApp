package com.example.note.feature_note.presentation.addNewNote.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.note.R
@Composable
fun AddImageButton(
    modifier: Modifier = Modifier,
    painterSource: Int,
    onclicked : () -> Unit
){
        IconButton(
            modifier = modifier,
            onClick = onclicked,
        ) {
            Icon(painter = painterResource(id = painterSource), contentDescription ="Add Image" )
        }
}