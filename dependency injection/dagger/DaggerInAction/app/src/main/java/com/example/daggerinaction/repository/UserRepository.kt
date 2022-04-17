package com.example.daggerinaction.repository

import com.example.daggerinaction.di.Scope
import com.example.daggerinaction.repository.local.LocalDataSource
import com.example.daggerinaction.repository.remote.RemoteDatasource
import javax.inject.Inject
import javax.inject.Singleton

@Scope
class UserRepository @Inject constructor(
    val localRepository: LocalDataSource,
    val remoteDatasource: RemoteDatasource
)