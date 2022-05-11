package com.example.roomwithrepositorypattern.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lists_table")
class ListItem(
    @PrimaryKey(autoGenerate = true) val id: Int?=null,
    @ColumnInfo(name = "word") val word: String

)