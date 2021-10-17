package com.yisweb.alsafka.helper

import android.app.Activity
import android.os.Build
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TimePicker
import com.yisweb.alsafka.helper.SharedPreferencesManager.getLanguage
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by µðšţãƒâ ™ on 04/08/2020.
 *  ->
 */

//fun ImageView.loadUrl(url: String?) {
//    url?.let {
//        if (it.isNotEmpty())
//            Glide.with(context)
//                .load(BuildConfig.URL_STORAGE + it)
//                .into(this)
//    }
//}
//
//fun ImageView.url(url: String?) {
//    url?.let {
//        if (it.isNotEmpty())
//            Glide.with(context)
//                .load(it)
//                .into(this)
//    }
//}
//
//fun ImageView.loadDrawable(@DrawableRes drawableId: Int?) {
//    drawableId?.let {
//        Glide.with(context)
//            .load(drawableId)
//            .into(this)
//    }
//}

fun Activity.fixLayoutDirection() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (getLanguage() == "ar")
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        else
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
    }
}

fun DatePicker.getDate(): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth)
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time).toString()
}

fun TimePicker.getTime(): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(calendar.time).toString()
}

fun DatePicker.dateFormat(): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth)
    return SimpleDateFormat("EEEE dd/MM/yyyy", Locale.getDefault()).format(calendar.time).toString()
}

fun TimePicker.timeFormat(): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    return SimpleDateFormat("HH:mm aa", Locale.getDefault()).format(calendar.time).toString()
}