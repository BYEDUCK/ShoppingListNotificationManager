package com.byeduck.shoppinglistnotificationprovider

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationService : Service() {

    private var notificationId = 0;

    override fun onBind(intent: Intent): IBinder {
        return object : Binder() {}
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val listId = intent?.extras?.getLong("listId", -1L) ?: -1L
        val notification = NotificationCompat.Builder(this, getString(R.string.channelId))
                .setContentTitle(getString(R.string.notificationTitle))
                .setContentText("Go to shopping list $listId")
                .setAutoCancel(true)
                .build()
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(notificationId++, notification)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        val notificationChannel = NotificationChannel(
                getString(R.string.channelId),
                getString(R.string.channelName),
                NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.description = getString(R.string.channelDescription)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}