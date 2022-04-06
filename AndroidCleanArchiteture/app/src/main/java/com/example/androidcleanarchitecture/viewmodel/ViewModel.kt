package com.example.androidcleanarchitecture.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidcleanarchitecture.repository.UseCases
import com.example.androidcleanarchitecture.util.Utils
import com.example.androidcleanarchitecture.webservices.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(private val sampleUseCase: UseCases) :
    ViewModel() {



    fun getSampleResponse() = flow {
        emit(State.LoadingState)
        try {
            delay(1000)
            emit(State.DataState(sampleUseCase()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Utils.resolveError(e))
        }
    }
}