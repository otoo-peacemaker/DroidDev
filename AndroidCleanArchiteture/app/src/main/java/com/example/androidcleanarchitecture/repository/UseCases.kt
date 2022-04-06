package com.example.androidcleanarchitecture.repository

import com.google.gson.JsonObject
import javax.inject.Inject

class UseCases @Inject constructor(
    private val apIs: APIs
) {
    suspend operator fun invoke(): JsonObject {
        val response = apIs.login()
        //here you can add some domain logic or call another UseCase
        return response
    }
}