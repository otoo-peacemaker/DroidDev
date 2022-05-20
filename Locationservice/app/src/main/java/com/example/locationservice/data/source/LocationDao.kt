package com.example.locationservice.data.source

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.locationservice.model.LocationDetails
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(locationDetails: LocationDetails)

    @Query("SELECT * FROM location_services ORDER BY id ASC")
    fun getLocation(): Flow<List<LocationDetails>>

    @Query("DELETE FROM location_services")
    suspend fun deleteAll()
}