package com.example.architecturaltemplate.di.module


import androidx.databinding.library.BuildConfig
import com.example.architecturaltemplate.network.RemoteDataSource
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
class AndroidModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(): RemoteDataSource = RemoteDataSource()

    @Provides
    @Singleton
    fun provideHttpLogging(): OkHttpClient {
        return OkHttpClient.Builder().also { client ->
            if(BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }
        }.build()
    }
}
