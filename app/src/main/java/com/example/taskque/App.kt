package com.example.taskque

import android.app.Application
import com.example.taskque.db.InMemoryStore
import com.google.firebase.FirebaseApp

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        InMemoryStore.initializePreference(this)
        FirebaseApp.initializeApp(this)
    }
}