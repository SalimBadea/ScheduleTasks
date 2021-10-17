package com.yisweb.alsafka.helper

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import java.util.*

object SharedPreferencesManager {
    private lateinit var prefs: SharedPreferences
    private const val PREFS_NAME = "params"
    private const val LANGUAGE = "language"
    private const val CLASS = "class"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    }

    fun saveLanguage(value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(LANGUAGE, value)
            commit()
        }
    }

    fun getLanguage(): String = prefs.getString(LANGUAGE, "en")!!

    fun isLang(value: Boolean) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putBoolean("is_lang", value)
            commit()
        }
    }


    fun isSkip(): Boolean = prefs.getBoolean("is_skip", false)

    fun isLang(): Boolean = prefs.getBoolean("is_lang", true)

    fun saveClass(value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(CLASS, value)
            commit()
        }
    }

    fun getClass(): String? {
        return prefs.getString(CLASS, "")
    }

    fun setLocal(context: Context) {
        val myLocal = Locale(getLanguage())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Locale.setDefault(Locale.forLanguageTag(getLanguage()))
        }
        val res = context.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocal

        res.updateConfiguration(conf, dm)
        conf.setLayoutDirection(myLocal)
    }
}