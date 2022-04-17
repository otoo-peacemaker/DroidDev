package com.example.basic.di

import com.example.basic.repository.UserRepository

// Create an instance of the application graph
val appGraph: AppComponentGraph = DaggerAppComponentGraph.create()
val userRepository: UserRepository = appGraph.userRepo()

