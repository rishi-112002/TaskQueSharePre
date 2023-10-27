package com.example.taskque.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.example.taskque.R
import com.example.taskque.ui.activity.HomePageViewActivity
import com.example.taskque.utils.Constants.MyIntents.SHARED_PREFERENCE_KEY
import com.example.taskque.utils.Constants.MyIntents.USER_EMAIL

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    private val TAG: String = "Splash_Screen"
    private lateinit var splashAnimationView : LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)
        splashAnimationView = findViewById(R.id.animationView)
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString(USER_EMAIL, null)
        var intent: Intent
        Handler(Looper.getMainLooper()).postDelayed({
            Log.d(TAG, userEmail.toString())
            intent = if (TextUtils.isEmpty(userEmail)) {
                Intent(this, LoginPage::class.java)
            } else {
                Intent(this, HomePageViewActivity::class.java)
            }
            startActivity(intent)
        }, 4000)


    }
}