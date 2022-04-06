package com.example.architecturaltemplate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architecturaltemplate.dao.LoginResponse
import com.example.architecturaltemplate.repository.LoginRepository
import com.triad.mvvmlearning.network.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private var repository: LoginRepository) : ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun login(body: HashMap<String, String>) = viewModelScope.launch {
        _loginResponse.value = repository.login(body)
    }

}