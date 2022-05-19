package com.example.roomwithrepositorypattern.repository

import androidx.annotation.WorkerThread
import com.example.roomwithrepositorypattern.dao.ListDao
import com.example.roomwithrepositorypattern.model.ListItem
import kotlinx.coroutines.flow.Flow

/**
 * What is Repository
 * A repository class abstracts access to multiple data sources.
 * The repository is not part of the Architecture Components libraries,
 * but is a suggested best practice for code separation and architecture.
 * A Repository class provides a clean API for data access to the rest of the application.
 *
 * Why repository?
 * A Repository manages queries and allows you to use multiple backends.
 * In the most common example, the Repository implements the logic for
 * deciding whether to fetch data from a network or use results cached in a local database.
 * */

/**Declares the DAO as a private property in the constructor. Pass in the DAO
 * instead of the whole database, because you only need access to the DAO*/
class ListRepository (private val listDao: ListDao){

    /**Room executes all queries on a separate thread.
     * Observed Flow will notify the observer when the data has changed.*/
    val allList: Flow<List<ListItem>> = listDao.getList()

    /**
     * By default Room runs suspend queries off the main thread, therefore, we don't need to
     * implement anything else to ensure we're not doing long running database work off the main thread.
     * */

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(listItem: ListItem){
        listDao.insert(listItem)
    }
}