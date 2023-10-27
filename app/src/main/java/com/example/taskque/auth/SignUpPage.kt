package com.example.taskque.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.taskque.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpPage : AppCompatActivity() {
    private val TAG: String = "SIGNUP_ACTIVITY"
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var signUpEmail: TextView
    private lateinit var password: TextView
    private lateinit var confirmPassword: TextView
    private lateinit var singUpButton: Button
    private lateinit var redirectToSignUp: TextView
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var fireAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)
        fireStore = FirebaseFirestore.getInstance()
        fireAuth = FirebaseAuth.getInstance()
        firstName = findViewById(R.id.firstName)
        lastName = findViewById(R.id.lastName)
        redirectToSignUp = findViewById(R.id.redirectToSignUp)
        signUpEmail = findViewById(R.id.signUpEmail)
        password = findViewById(R.id.signUpPassword)
        confirmPassword = findViewById(R.id.singnUpConfirmPassword)
        singUpButton = findViewById(R.id.signUpButton)
        singUpButton.setOnClickListener {
            userSignUp()
        }
        redirectToSignUp.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

    }

    private fun userSignUp() {
        val userFirstName = firstName.text.toString()
        val userLastName = lastName.text.toString()
        val userEmail = signUpEmail.text.toString()
        val userPassword = password.text.toString()
        val userConfirmPassword = confirmPassword.text.toString()
        if (userPassword != userConfirmPassword) {
            Toast.makeText(this, "password is not match please check password", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (userPassword.isBlank() || userConfirmPassword.isBlank() || userEmail.isBlank() || userFirstName.isBlank() || userLastName.isBlank()) {
            Toast.makeText(this, "please fill all required detail's", Toast.LENGTH_SHORT).show()
            return
        }

        fireAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val user = fireAuth.currentUser

                    val userData = hashMapOf(
                        "userEmail" to userEmail,
                        "userPassword" to userPassword,
                        "firstName" to userFirstName,
                        "lastName" to userLastName
                    )

                    user!!.uid.let {
                        fireStore.collection("user").document(userEmail).set(userData)
                            .addOnSuccessListener {
                                Log.d(TAG, "User added successfully")
                            }.addOnFailureListener {
                                Log.e(TAG, "Failed to add user")
                            }
                    }
                    finish()
                } else {
                    if (userPassword.length < 6) {
                        Toast.makeText(this, "password is must of 6 digits", Toast.LENGTH_SHORT)
                            .show()
                    }
                    val pattern = Patterns.EMAIL_ADDRESS
                    if (!pattern.matcher(userEmail).matches()) {
                        Toast.makeText(this, "email is not valid", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this,
                            "user is already registered  please try to login",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@addOnCompleteListener
                }
            }
    }

}