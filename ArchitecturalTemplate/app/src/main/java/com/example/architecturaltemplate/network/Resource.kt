package com.example.architecturaltemplate.network

import okhttp3.ResponseBody

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int? = null,
        val errorMessage: String? = null,
        val errorBody: ResponseBody? = null
    ) : Resource<Nothing>()
}
