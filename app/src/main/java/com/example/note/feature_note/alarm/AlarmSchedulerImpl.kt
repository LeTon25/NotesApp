package com.example.note.feature_note.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.note.feature_note.broadcast.AlarmReceiver
import com.example.note.feature_note.domain.model.AlarmItem
import java.time.ZoneId

class AlarmSchedulerImpl(
    private val context:Context
) : AlarmSheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    override fun schedule(item: AlarmItem) {
        val intent = Intent(context,AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE",item.message)
            putExtra("EXTRA_TITLE",item.title)
        }
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                     PendingIntent.getBroadcast(context,item.hashCode(),intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            )
    }

    override fun cancel(item: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(context,item.hashCode(),Intent(),PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        )
    }
}