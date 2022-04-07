package com.example.architecturaltemplate.app

import android.app.Application
import android.content.Context
import com.example.architecturaltemplate.di.component.DaggerAppComponent
import com.example.architecturaltemplate.di.module.AndroidModule
import com.werockstar.dagger2demo.di.component.AppComponent
import com.example.architecturaltemplate.di.module.ApplicationModule
import dagger.android.DaggerApplication

class App: Application() {
    init {
        instance = this
    }

    companion object{
        var instance: App? =null

        fun applicationContext(): Context{
            return instance!!.applicationContext
        }
    }

    lateinit var  component: AppComponent

    override fun onCreate() {
        super.onCreate()
        createComponent()
    }

   protected open fun createComponent() {
        component = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))

            .androidModule(AndroidModule())            .build()
    }
}