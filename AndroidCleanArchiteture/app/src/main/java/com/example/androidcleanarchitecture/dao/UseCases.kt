package com.example.androidcleanarchitecture.dao

import com.example.androidcleanarchitecture.repository.APIs
import com.google.gson.JsonObject
import javax.inject.Inject

class UseCases @Inject constructor(
    private val apIs: APIs
) {
    suspend operator fun invoke(): JsonObject {
        val response = apIs.sampleGet()
        //here you can add some domain logic or call another UseCase
        return response
    }
}