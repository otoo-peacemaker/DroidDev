package com.example.architecturaltemplate.di.module


import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.example.architecturaltemplate.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Application = this.application

    @Provides
    @Singleton
    fun provideInputMethod() =
        application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


}
