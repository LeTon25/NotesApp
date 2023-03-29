package com.example.note.feature_note.presentation.addNewNote.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter


@Composable
fun ImageField(
    modifier :Modifier = Modifier,
    uri : String,
){
    Image(modifier = modifier,painter = rememberAsyncImagePainter(model = uri), contentDescription ="Hình ảnh" )
}