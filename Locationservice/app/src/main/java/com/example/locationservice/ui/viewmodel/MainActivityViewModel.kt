package com.example.locationservice.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.locationservice.data.repository.LocationRepository
import com.example.locationservice.model.LocationDetails
import kotlinx.coroutines.launch

class MainActivityViewModel(private val locationRepository: LocationRepository) : ViewModel() {

    fun insertIntoRoom(locationDetails: LocationDetails) = viewModelScope.launch {
        locationRepository.addLocation(locationDetails)
    }

    val getDetails: LiveData<List<LocationDetails>> = locationRepository.getLocation().asLiveData()

}