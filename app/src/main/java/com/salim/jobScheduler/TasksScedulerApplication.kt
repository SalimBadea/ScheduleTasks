package com.salim.jobScheduler

import androidx.multidex.MultiDexApplication

import com.yisweb.alsafka.helper.SharedPreferencesManager.init
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

class TasksScedulerApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
//        FacebookSdk.sdkInitialize(applicationContext)
        init(applicationContext)

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("font/Cairo-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                ).build()
        );
    }


}