package com.example.locationservice.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.locationservice.data.source.LocationDao
import com.example.locationservice.model.LocationDetails
import kotlinx.coroutines.flow.Flow

class LocationRepository(private val locationDao: LocationDao) : LocationDao {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun addLocation(locationDetails: LocationDetails) {
        locationDao.addLocation(locationDetails)
    }

    override fun getLocation(): Flow<List<LocationDetails>> {
        return locationDao.getLocation()
    }

    override suspend fun deleteAll() {
        return locationDao.deleteAll()
    }
}