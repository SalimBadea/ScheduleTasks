package com.salim.jobScheduler.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.salim.jobScheduler.R
import com.yisweb.alsafka.helper.SharedPreferencesManager
import kotlinx.android.synthetic.main.activity_language.*
import maes.tech.intentanim.CustomIntent

class LanguageActivity : BaseActivity(), View.OnClickListener {

    override fun loadLayoutResource(): Int = R.layout.activity_language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomIntent.customType(this, "up-to-bottom")
        tvArabic.setOnClickListener(this)
        tvEnglish.setOnClickListener(this)
        lang_layout.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v){
            tvArabic -> {
                SharedPreferencesManager.saveLanguage("ar")
                startActivity(Intent(this, SplashActivity::class.java))
                finishAffinity()
            }
            tvEnglish -> {
                SharedPreferencesManager.saveLanguage("en")
                startActivity(Intent(this, SplashActivity::class.java))
                finishAffinity()
            }
            lang_layout -> {
                finish()
            }
        }
    }
}