package com.example.androidcleanarchitecture.repository

import com.google.gson.JsonObject
import retrofit2.http.GET

interface APIs {
    @GET("api/users")
    suspend fun sampleGet(): JsonObject
}