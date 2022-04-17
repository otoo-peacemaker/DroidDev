package com.example.architecturaltemplate.repository

import android.accounts.NetworkErrorException
import com.example.architecturaltemplate.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () ->T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())

            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(
                            false,
                            throwable.code(),
                            throwable.message ?: "Http connection error",
                            throwable.response()?.errorBody()
                        )
                    }
                    else -> {
                        Resource.Failure(true, null, throwable.message ?: "connecting...",)
                    }
                }
            } catch (e: HttpException) {
                Resource.Failure(false, errorMessage = e.message ?: "Something went wrong")
            } catch (e: SocketTimeoutException) {
                Resource.Failure(false, errorMessage = e.message ?: "connection error!")
            } catch (e: ConnectException) {
                Resource.Failure(false, errorMessage = e.message ?: "no internet access")
            } catch (e: UnknownHostException) {
                Resource.Failure(false, errorMessage = e.message ?: "connection error")
            }
        }

    }


    suspend fun <T> safeApiCall2(apiCall: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            apiCall.invoke()

        }
    }
}