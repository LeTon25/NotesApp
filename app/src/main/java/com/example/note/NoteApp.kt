package com.example.note

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApp : Application() {
    companion object {
        const val CHANNEL_ID = "NoteApp"
    }
    override fun onCreate() {
        super.onCreate()
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNoficationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNoficationChannel() {
        val channel = NotificationChannel(NoteApp.CHANNEL_ID,"Note app",NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

}