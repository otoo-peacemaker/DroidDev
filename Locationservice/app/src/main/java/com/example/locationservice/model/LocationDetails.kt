package com.example.locationservice.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "location_services")
data class LocationDetails(
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "date") val date: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}