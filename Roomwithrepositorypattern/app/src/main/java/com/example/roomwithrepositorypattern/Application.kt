package com.example.roomwithrepositorypattern

import android.app.Application
import com.example.roomwithrepositorypattern.db.AppDatabase
import com.example.roomwithrepositorypattern.repository.ListRepository
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
    val repository by lazy { ListRepository(db.listDao()) }
}