package com.example.locationservice.ui.viewmodel

import androidx.lifecycle.*
import com.example.locationservice.data.repository.LocationRepository
import com.example.locationservice.model.LocationDetails
import kotlinx.coroutines.launch

class ActivityViewModel(private val locationRepository: LocationRepository) : ViewModel() {

    private val _details: MutableLiveData<LocationDetails> = MutableLiveData()
    val details: LiveData<LocationDetails> get() = _details

    /*fun insertIntoDb(locationDetails: LocationDetails) = viewModelScope.launch {
        locationRepository.addLocation(_details)
    }*/


    fun insertIntoRoom(locationDetails: LocationDetails) = viewModelScope.launch {
        locationRepository.addLocation(locationDetails)
    }

    val getDetails: LiveData<List<LocationDetails>> = locationRepository.getLocation().asLiveData()

}