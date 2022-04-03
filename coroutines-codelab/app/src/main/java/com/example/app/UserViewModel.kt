package com.example.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class UserViewModel(val userRepository: UserRepository) : ViewModel(
) {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // onError("Exception handled: ${throwable.localizedMessage}")
        _status.value = NetworkStatus.ERROR
    }

    /**
     * @param [status]
     * The internal MutableLiveData that stores the status of the most recent request
     * The external immutable LiveData for the request status
     * */

    private val _status = MutableLiveData<NetworkStatus>()
    val status: LiveData<NetworkStatus>
        get() = _status

    /**
     * Internally, we use a MutableLiveData, because we will be updating the  UserResponses
     * with new values
     * The external LiveData interface to the property is immutable, so only this class can modify
     * */

    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse>
        get() = _userResponse

    /**
     * Gets filtered Mars real estate property information from the Mars API Retrofit service and
     * updates the [UserResponse] [List] and [NetworkStatus] [LiveData]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     */

    fun login(userBody: UserBody) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            _status.value = NetworkStatus.LOADING
            withContext(Dispatchers.Main){
               _userResponse.value = userRepository.login(userBody)
                _status.value = NetworkStatus.SUCCESS
            }
        }
    }

}
