package com.example.note.feature_note.domain.model

import java.time.LocalDateTime

data class AlarmItem(
    val time : LocalDateTime,
    val title : String,
    val message : String
)
