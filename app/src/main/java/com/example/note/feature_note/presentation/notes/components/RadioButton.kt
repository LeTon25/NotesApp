package com.example.note.feature_note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun  CustomRadioButton(
    text:String,
    selected:Boolean,
    onSelected : ()->Unit,
    modifier: Modifier =Modifier
) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically)
    {
        RadioButton(
            selected = selected,
            onClick = {  onSelected },
            colors =RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(modifier = modifier.width(4.dp))
        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }
}