package com.byeduck.shoppinglistnotificationprovider

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ModificationEventReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Received broadcast", Toast.LENGTH_SHORT).show()
        val listId = intent.getLongExtra("listId", -1L)
        val elemTxt = intent.getStringExtra("elemTxt")
        val elemId = intent.getLongExtra("elemId", -1L)
        val serviceIntent = Intent(context, NotificationService::class.java).apply {
            putExtra("listId", listId)
            putExtra("elemTxt", elemTxt)
            putExtra("elemId", elemId)
        }
        context.startForegroundService(serviceIntent)
    }

}