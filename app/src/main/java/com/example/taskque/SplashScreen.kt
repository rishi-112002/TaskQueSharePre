package com.example.taskque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.taskque.ui.activity.HomePageViewActivity

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    lateinit var splashText :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN ,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)
        splashText = findViewById(R.id.splashScrrenText)
        val animation = AnimationUtils.loadAnimation(this , R.anim.text_fade_animation)
     animation.reset()
        splashText.clearAnimation()
        splashText.startAnimation(animation)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(
                this, HomePageViewActivity::class.java
            )
            startActivity(intent)
        } , 3000)


    }
}