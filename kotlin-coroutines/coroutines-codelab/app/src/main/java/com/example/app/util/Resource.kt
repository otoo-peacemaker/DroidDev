package com.example.app.util

import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Peacemaker Otoo
 * This class is responsible for communicating with the current state of network call to the UI
 * */


 data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object{
         fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(
            msg: String?= null, data: T? = null,
            isNetworkErr: Boolean? = null,
            errCode: Int? = null,
            errBody: ResponseBody? = null
        ): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }



}