package com.salim.myapp.utils

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.*

/**
 * Created by devdeeds.com on 5/12/17.
 */

class NotificationUtils {

    fun setNotification(timeInMilliSeconds: Long, activity: Activity) {

        //------------  alarm settings start  -----------------//
        if (timeInMilliSeconds > 0) {

            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(
                activity.applicationContext,
                MyAlarmService::class.java
            )
            // AlarmReceiver1 = broadcast receiver

            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", timeInMilliSeconds)

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMilliSeconds

            val pendingIntent = PendingIntent.getBroadcast(
                activity,
                0,
                alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilliSeconds, pendingIntent)
        }
    }
}