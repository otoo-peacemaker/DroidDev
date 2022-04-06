package com.example.app.repository.RemoteDataSource

import com.example.app.network.APIServices
import com.example.app.network.BackendApi
import com.example.app.network.SafeApiCall

abstract class BaseRepository(private val api: BackendApi) : SafeApiCall {
    suspend fun logout() = safeApiCall {
        api.retrofitService.logout()
    }
}