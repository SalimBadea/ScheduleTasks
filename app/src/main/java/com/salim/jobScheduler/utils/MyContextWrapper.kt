package com.salim.jobScheduler.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import java.util.Locale
import android.os.LocaleList
import com.yisweb.alsafka.helper.SharedPreferencesManager.getLanguage
import io.github.inflationx.viewpump.ViewPumpContextWrapper

/**
 * Created by µðšţãƒâ ™ on 04/08/2020.
 *  ->
 */
class MyContextWrapper(base: Context?) : ContextWrapper(base) {

    companion object {
        @SuppressWarnings("deprecation")
        fun wrap(baseContext: Context?): ContextWrapper {
            var context = baseContext
            val res = baseContext!!.resources
            val configuration = res.configuration
            val newLocale = Locale(getLanguage())
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                    configuration.setLocale(newLocale)

                    val localeList = LocaleList(newLocale)
                    LocaleList.setDefault(localeList)
                    configuration.setLocales(localeList)
                    context = baseContext.createConfigurationContext(configuration)

                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    configuration.setLocale(newLocale)
                    context = baseContext.createConfigurationContext(configuration)

                }
                else -> {
                    configuration.locale = newLocale
                    res.updateConfiguration(configuration, res.displayMetrics)
                }
            }
            return MyContextWrapper(ViewPumpContextWrapper.wrap(context!!))
        }
    }
}