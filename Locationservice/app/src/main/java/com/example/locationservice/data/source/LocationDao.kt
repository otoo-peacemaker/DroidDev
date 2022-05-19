package com.example.locationservice.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.locationservice.model.LocationDetails
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLocation(locationDetails: LocationDetails)

    @Query("SELECT * FROM location_service ORDER BY id ASC")
    fun getLocation(): Flow<List<LocationDetails>>

    @Query("DELETE FROM location_service")
    suspend fun deleteAll()
}