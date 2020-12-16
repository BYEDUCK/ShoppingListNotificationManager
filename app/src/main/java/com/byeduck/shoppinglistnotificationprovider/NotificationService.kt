package com.byeduck.shoppinglistnotificationprovider

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class NotificationService : Service() {

    private var notificationId = 0;

    override fun onBind(intent: Intent): IBinder {
        return object : Binder() {}
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = createNotificationChannel()
        val listId = intent?.extras?.getString("listId") ?: ""
        val elemTxt = intent?.extras?.getString("elemTxt") ?: ""
        val elemId = intent?.extras?.getString("elemId") ?: ""
        val activityIntent = Intent("${getString(R.string.mainPackage)}.ADD_EDIT_LIST").apply {
            putExtra("listId", listId)
            putExtra("elemId", elemId)
            component = ComponentName(
                getString(R.string.mainPackage),
                "${getString(R.string.mainPackage)}.detail.AddEditShoppingElementActivity"
            )
        }
        val pendingIntent = PendingIntent.getActivity(
            this, System.currentTimeMillis().toInt(), activityIntent, FLAG_CANCEL_CURRENT
        )
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Shopping list ${listId.substring(0, 4)} updated")
            .setContentText(
                "Element(${
                    elemId.substring(
                        0,
                        4
                    )
                }) \"$elemTxt\" has been added. Edit it!"
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(notificationId++, notification)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel(): String {
        val channelId = UUID.randomUUID().toString()
        val notificationChannel = NotificationChannel(
            channelId,
            getString(R.string.channelName),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.description = getString(R.string.channelDescription)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(notificationChannel)
        return channelId
    }
}