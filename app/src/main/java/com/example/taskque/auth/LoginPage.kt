package com.example.taskque.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.taskque.R
import com.example.taskque.ui.activity.HomePageViewActivity
import com.example.taskque.utils.Constants.MyIntents.SHARED_PREFERENCE_KEY
import com.example.taskque.utils.Constants.MyIntents.USER_EMAIL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginPage : AppCompatActivity() {
    private val TAG: String = "LOGIN_PAGE_ACTIVITY"
    lateinit var loginEmail: TextView
    lateinit var loginPassword: TextView
    lateinit var loginButton: Button
    lateinit var rediretToSignUp: TextView
    lateinit var fireStore: FirebaseFirestore
    lateinit var fireAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        fireStore = FirebaseFirestore.getInstance()
        fireAuth = FirebaseAuth.getInstance()
        loginEmail = findViewById(R.id.userEmail)
        loginPassword = findViewById(R.id.userPassword)
        loginButton = findViewById(R.id.loginButton)
        rediretToSignUp = findViewById(R.id.redirectToSignUp)
        rediretToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpPage::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = loginEmail.text.toString()
        val password = loginPassword.text.toString()
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "please fill all required detail's", Toast.LENGTH_SHORT).show()
            return
        }
        fireAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            Log.d(TAG, "success")
            Toast.makeText(this, "login successfully", Toast.LENGTH_SHORT).show()
            val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(USER_EMAIL, email)
            editor.apply()
            val intent = Intent(
                this, HomePageViewActivity::class.java
            )
            startActivity(intent)
        }.addOnFailureListener {
            Log.d(TAG, "failed")
            Toast.makeText(this, "incorrect detail's", Toast.LENGTH_SHORT).show()

        }

    }
}
