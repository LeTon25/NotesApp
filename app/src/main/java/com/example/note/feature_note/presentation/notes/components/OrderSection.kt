package com.example.note.feature_note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.note.feature_note.domain.util.NoteOrder
import com.example.note.feature_note.domain.util.OrderType

@Composable
fun OrderSection(modifier: Modifier = Modifier ,
                noteOrder: NoteOrder = NoteOrder.Date(orderType = OrderType.Descending),
                onChangeOrder : (NoteOrder) -> Unit
                 ){
        Column(modifier = modifier) {
             Row(modifier = modifier.fillMaxWidth()
             ) {
                 CustomRadioButton(text = "Title", selected = noteOrder is NoteOrder.Title, onSelected = {
                     onChangeOrder(NoteOrder.Title(noteOrder.orderType))
                 })
                 CustomRadioButton(text = "Date", selected = noteOrder is NoteOrder.Date, onSelected = {
                     onChangeOrder(NoteOrder.Date(noteOrder.orderType))
                 })
                 CustomRadioButton(text = "Colors", selected = noteOrder is NoteOrder.Color, onSelected = {
                     onChangeOrder(NoteOrder.Color(noteOrder.orderType))
                 })
             }
            Row(modifier = modifier.fillMaxWidth()

            ) {
                CustomRadioButton(text = "Ascending", selected = noteOrder.orderType is OrderType.Ascending, onSelected = {
                    onChangeOrder(noteOrder.copy(orderType = OrderType.Ascending))
                })
                CustomRadioButton(text = "Decending", selected = noteOrder.orderType is OrderType.Descending, onSelected = {
                    onChangeOrder(noteOrder.copy(orderType = OrderType.Descending))
                })
            }
        }
}
