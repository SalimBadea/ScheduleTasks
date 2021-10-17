package com.salim.jobScheduler.utils

import android.app.ActivityManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log

class MyAlarmService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        isAppsIsInBackground(context)
        val service = Intent(context, NotificationService::class.java)
        service.putExtra("reason", intent.getStringExtra("reason"))
        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))

        context.startService(service)
    }

//    override fun onReceive(context: Context, intent: Intent) {
//        isAppsIsInBackground(context)
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val notification: Notification = intent.getParcelableExtra(NOTIFICATION)!!
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val importance = NotificationManager.IMPORTANCE_HIGH
//            val notificationChannel = NotificationChannel(
//                NOTIFICATION_CHANNEL_ID,
//                "NOTIFICATION_CHANNEL_NAME",
//                importance
//            )
//            notificationManager.createNotificationChannel(notificationChannel)
//        }
//        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
//        notificationManager.notify(id, notification)
//    }
//
    private fun isAppsIsInBackground(context: Context) {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcessInfo = am.runningAppProcesses

        for (i in runningAppProcessInfo.indices) {
            Log.e("MediaRecorder", "package = <${runningAppProcessInfo[i].processName}>")
        }
    }

}