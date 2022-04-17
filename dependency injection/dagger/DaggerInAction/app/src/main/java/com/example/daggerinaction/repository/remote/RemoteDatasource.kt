package com.example.daggerinaction.repository.remote

import com.example.daggerinaction.network.WebService
import javax.inject.Inject

class RemoteDatasource @Inject constructor(
    private val webService: WebService
){}