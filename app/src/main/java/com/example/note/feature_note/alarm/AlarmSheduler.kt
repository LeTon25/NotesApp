package com.example.note.feature_note.alarm

import com.example.note.feature_note.domain.model.AlarmItem

interface AlarmSheduler {
    fun schedule(item : AlarmItem)
    fun cancel(item: AlarmItem)
}