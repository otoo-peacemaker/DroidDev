package com.example.roomwithrepositorypattern.dao

import androidx.room.*
import com.example.roomwithrepositorypattern.model.ListItem
import kotlinx.coroutines.flow.Flow


@Dao
interface ListDao {
    @Query("SELECT * FROM lists_table ORDER BY word ASC")
    fun getList(): Flow<List<ListItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(listItem: ListItem)

    @Update
    suspend fun updateList(listItem: ListItem)

    @Query("DELETE FROM lists_table")
    suspend fun deleteAll()
/*
    @Delete
    suspend fun deleteList(listItem: ListItem)*/

}