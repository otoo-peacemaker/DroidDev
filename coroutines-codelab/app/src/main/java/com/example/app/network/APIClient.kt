package com.example.app.network

import com.example.app.util.Endpoints
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Endpoints.BASE_URL)
        .build()

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object BackendApi {
    val retrofitService : APIServices by lazy { retrofit.create(APIServices::class.java) }
}