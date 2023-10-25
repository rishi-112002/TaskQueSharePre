package com.example.taskque

import android.app.Application
import com.example.taskque.db.InMemoryStore

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        InMemoryStore.initalizePreference(this)
    }
}