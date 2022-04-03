package com.example.app

class UserRepository ( val apiServices: BackendApi) {
    suspend fun login(userBody: UserBody) = apiServices.retrofitService.login(userBody)
}