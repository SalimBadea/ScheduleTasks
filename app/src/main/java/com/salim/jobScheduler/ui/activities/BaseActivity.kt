package com.salim.jobScheduler.ui.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.salim.jobScheduler.utils.MyContextWrapper
import com.yisweb.alsafka.helper.*

/**
 * Created by µðšţãƒâ ™ on 04/08/2020.
 *  ->
 */
abstract class BaseActivity : AppCompatActivity() {
    abstract fun loadLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (loadLayoutResource() != -1)
            setContentView(loadLayoutResource())
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase))
    }

    override fun onResume() {
        super.onResume()
        SharedPreferencesManager.setLocal(this@BaseActivity)
        SharedPreferencesManager.isLang(true)
        fixLayoutDirection()

    }


}