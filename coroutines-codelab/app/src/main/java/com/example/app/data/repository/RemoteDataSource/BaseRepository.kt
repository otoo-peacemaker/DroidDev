package com.example.app.data.repository.RemoteDataSource

import com.example.app.network.APIServices
import com.example.app.network.BackendApi
import com.example.app.network.SafeApiCalls

abstract class BaseRepository(private val api: BackendApi) : SafeApiCalls {
    suspend fun logout() = safeApiCalls {
        api.retrofitService.logout()
    }
}