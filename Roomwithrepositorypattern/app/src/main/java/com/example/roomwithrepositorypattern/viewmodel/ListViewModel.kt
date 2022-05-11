package com.example.roomwithrepositorypattern.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomwithrepositorypattern.model.ListItem
import com.example.roomwithrepositorypattern.repository.ListRepository
import kotlinx.coroutines.launch

/**
 * The ViewModelFactory's role is to provide data to the UI and survive configuration changes.
 * A ViewModelFactory acts as a communication center between the Repository and the UI.
 * You can also use a ViewModelFactory to share data between fragments
 * */

class ListViewModel (private val repository: ListRepository): ViewModel() {
    /**
     * Using LiveData and caching what allList returns has several benefits:
     * - We can put an observer on the data (instead of polling for changes)
     * and only update the the UI when the data actually changes.
     * - Repository is completely separated from the UI through the ViewModelFactory.
     * */
    val allList: LiveData<List<ListItem>> = repository.allList.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(listItem: ListItem) = viewModelScope.launch {
        repository.insert(listItem)
    }
}