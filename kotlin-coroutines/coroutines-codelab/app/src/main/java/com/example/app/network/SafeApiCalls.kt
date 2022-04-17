package com.example.app.network

import com.example.app.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/*
interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkStatus<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkStatus.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        NetworkStatus.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }
                    else -> {
                        NetworkStatus.Failure(true, null, null)
                    }
                }
            }
        }
    }
}
*/

interface SafeApiCalls {
    suspend fun <T> safeApiCalls(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.Unconfined) {
            try {
                Resource.success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.error(
                            isNetworkErr = false,
                            msg = throwable.message(),
                            errBody = throwable.response()?.errorBody(),
                            errCode = throwable.code()
                        )
                    }
                    else -> {
                        Resource.error(
                            isNetworkErr = true,
                            msg = null,
                            errCode = null,
                            errBody = null
                        )
                    }
                }
            }
        }
    }
}