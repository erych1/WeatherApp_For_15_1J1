package com.ulan.weatherapp_for_15_1j.ui.splashScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ulan.weatherapp_for_15_1j.R
import com.ulan.weatherapp_for_15_1j.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class ActivitySplashScreen : AppCompatActivity() {

    private val SPLASH_SCREEN_TIME : Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.myLooper()!!).postDelayed({
                startActivity(Intent(this , MainActivity::class.java))
                finish()
        }, SPLASH_SCREEN_TIME)
    }


}