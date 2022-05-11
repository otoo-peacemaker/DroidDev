package com.example.roomwithrepositorypattern.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomwithrepositorypattern.dao.ListDao
import com.example.roomwithrepositorypattern.model.ListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Room is a database layer on top of an SQLite database.
 * Room takes care of mundane tasks that you used to handle with an SQLiteOpenHelper.
 * Room uses the DAO to issue queries to its database.
 * By default, to avoid poor UI performance, Room doesn't allow you to issue queries on the main thread. When Room queries return Flow, the queries are automatically run asynchronously on a background thread.
 * Room provides compile-time checks of SQLite statements
 *
 * [Note] Your Room database class must be abstract and should extend RoomDatabase.
 * Usually, you only need one instance of a Room database for the whole app.
 * */

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [ListItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao

    class ListDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val listDao = database.listDao()
                    //delete the database
                    listDao.deleteAll()
                    //add data
                    var data = ListItem(word = "location")
                    listDao.insert(data)
                    data = ListItem(word = "Updated")
                    listDao.insert(data)
                }
            }
        }

        /*private suspend fun populateDatabase(listDao: ListDao) {
            //  listDao.deleteAll()// delete all content
        }*/
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lists_table"
                ).addCallback(ListDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
