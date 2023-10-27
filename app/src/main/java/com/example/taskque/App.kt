package com.example.taskque

import android.app.Application
import android.content.Context
import com.example.taskque.db.InMemoryStore
import com.google.firebase.FirebaseApp

class App : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }


    override fun onCreate() {
        super.onCreate()
        InMemoryStore.initializePreference(this)
        FirebaseApp.initializeApp(this)

      val    context = applicationContext()
    }
}