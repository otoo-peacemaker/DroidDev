package com.example.app
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIServices {
    @Headers("Accept: application/json", "Content-Type:application/json")
    @POST("login")
    suspend fun login(@Body params: UserBody): UserResponse

}