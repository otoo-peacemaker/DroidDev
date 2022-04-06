package com.werockstar.dagger2demo.di.component

import com.example.architecturaltemplate.MainActivity
import com.example.architecturaltemplate.di.module.ActivityModule
import com.example.architecturaltemplate.di.module.AndroidModule
import com.example.architecturaltemplate.di.module.ApplicationModule
import com.triad.mvvmlearning.network.RemoteDataSource

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ ApplicationModule::class,
    AndroidModule::class, ActivityModule::class])

interface AppComponent {
    fun inject(fragment: MainActivity)

   // fun inject(activity: LoginFragment)

   fun inject(fragment: RemoteDataSource)

}