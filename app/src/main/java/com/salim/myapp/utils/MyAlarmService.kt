package com.salim.myapp.utils

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat

class MyAlarmService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val serviceIntent = Intent(context, NotificationService::class.java)
//            serviceIntent.putExtra("alarmName", "alarmName")
//            ContextCompat.startForegroundService(context, serviceIntent)
//        } else {
//            val serviceIntent = Intent(context, NotificationService::class.java)
//            context.stopService(serviceIntent)
//            serviceIntent.putExtra("alarmName", "alarmName")
//            context.startService(serviceIntent)
//        }

        val service = Intent(context, NotificationService::class.java)
        service.putExtra("reason", intent.getStringExtra("reason"))
        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))

        context.startService(service)
    }

}