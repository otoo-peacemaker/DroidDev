package com.example.app

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.repository.RemoteDataSource.UserRepository
import com.example.app.util.snackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*

@HiltViewModel
class UserViewModel(var userRepository: UserRepository) : ViewModel(
) {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
//        _status.value = throwable.localizedMessage
    }

    /**
     * @param [status]
     * The internal MutableLiveData that stores the status of the most recent request
     * The external immutable LiveData for the request status
     * */

    private val _status = MutableLiveData<NetworkStatus<UserResponse>>()
    val status: LiveData<NetworkStatus<UserResponse>>
        get() = _status

    /**
     * Internally, we use a MutableLiveData, because we will be updating the  UserResponses
     * with new values
     * The external LiveData interface to the property is immutable, so only this class can modify
     * */

    private val _userResponse = MutableLiveData<NetworkStatus<UserResponse>>()
    val userResponse: LiveData<NetworkStatus<UserResponse>>
        get() = _userResponse

    /**
     * Gets filtered Mars real estate property information from the Mars API Retrofit service and
     * updates the [UserResponse] [List] and [NetworkStatus] [LiveData]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     */

    fun login(userBody: UserBody) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            _status.value = NetworkStatus.Loading
            withContext(Dispatchers.Main) {
                _userResponse.value = userRepository.login(userBody)
            }
        }
    }

    fun register(userBody: UserBody) = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        _status.value = NetworkStatus.Loading
        withContext(Dispatchers.Main) {
            _userResponse.value = userRepository.login(userBody)
        }
    }

    private fun onError(message: String) {
        val view: View? = null
        view?.snackBar(message)
    }

}
