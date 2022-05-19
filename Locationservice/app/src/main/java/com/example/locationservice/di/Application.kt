package com.example.locationservice.di

import android.app.Application
import com.example.locationservice.data.AppDatabase
import com.example.locationservice.data.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * You want to have only one instance of the database and of the repository in your app.
 * An easy way to achieve this is by creating them as members of the Application class.
 * Then they will just be retrieved from the Application whenever they're needed, rather than constructed every time.
 * */

class App : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

    private val appScope = CoroutineScope(SupervisorJob())
    private val db by lazy { AppDatabase.getDatabase(this, appScope) }
    val repository by lazy { LocationRepository(db.locationDao()) }
}