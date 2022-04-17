package com.example.daggerinaction.di.module

import com.example.daggerinaction.di.Scope
import com.example.daggerinaction.network.WebService
import dagger.Module
import dagger.Provides


/**
 * @module: informs Dagger that this class is a Dagger Module
 *
 * @provides: Apart from [@Inject], we use the module annotation to tell dagger
 * to provide instance of a classes that your project doesn't own,
 * here, when we're calling the class outside[calling a network class]
 * */

@Module
class NetworkModule {
    @Scope
    @Provides
    fun provideWebService(): WebService{
        /*/return Retrofit.Builder()
                .baseUrl("https://example.com")
                .build()
                .create(LoginService::class.java)

         */

        return WebService()
    }


    /*
    @Scope
    @Provides
    fun provideLoginRetrofitService(
        okHttpClient: OkHttpClient
    ): LoginRetrofitService { ... }
    * */



}
