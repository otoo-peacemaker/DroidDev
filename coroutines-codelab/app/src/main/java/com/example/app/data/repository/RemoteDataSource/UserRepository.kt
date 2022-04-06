package com.example.app.repository.RemoteDataSource

import com.example.app.*
import com.example.app.network.APIServices
import com.example.app.network.BackendApi
import com.example.app.repository.local.UserPreferences

class UserRepository (
    var apiServices: BackendApi,
    private val preferences: UserPreferences)
    : BaseRepository(apiServices){
    suspend fun login(userBody: UserBody)  = safeApiCall{
        apiServices.retrofitService.login(userBody)
    }

    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        preferences.saveAccessTokens(accessToken, refreshToken)
    }
}