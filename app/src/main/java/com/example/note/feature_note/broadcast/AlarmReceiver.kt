package com.example.note.feature_note.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.note.NoteApp
import com.example.note.R

class AlarmReceiver :  BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra("EXTRA_MESSAGE")!!
            val title = intent?.getStringExtra("EXTRA_TITLE")!!

            val notificationManager = context!!.getSystemService(NotificationManager::class.java) as NotificationManager
            val notification = NotificationCompat.Builder(context,NoteApp.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_noti)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            notificationManager.notify(0,notification)

    }
}