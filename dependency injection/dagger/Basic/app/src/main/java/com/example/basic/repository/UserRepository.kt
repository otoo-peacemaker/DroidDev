package com.example.basic.repository

import com.example.basic.di.ProvidersScope
import javax.inject.Inject


/**@constructor [localDataSource, remoteDataSource]: make use of its parent class without the need of calling them
 * so the UserRepository provides them in its constructor so we can access them directly.
 * */
@ProvidersScope
class UserRepository @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
)