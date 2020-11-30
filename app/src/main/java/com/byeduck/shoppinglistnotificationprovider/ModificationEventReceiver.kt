package com.byeduck.shoppinglistnotificationprovider

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ModificationEventReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, NotificationService::class.java)
        val listId = intent.getLongExtra("listId", -1L)
        serviceIntent.putExtra("listId", listId)
        context.startService(serviceIntent)
    }

}