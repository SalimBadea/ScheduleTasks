package com.salim.myapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.salim.myapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val th: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                        val i = Intent(baseContext, MainActivity::class.java)
                        startActivity(i)
                        finish()

                } catch (e: Exception) {
                    e.message
                }
            }
        }
        th.start()
    }
}