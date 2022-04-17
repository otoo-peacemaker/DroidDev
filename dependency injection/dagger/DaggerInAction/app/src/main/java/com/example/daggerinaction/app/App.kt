package com.example.daggerinaction.app

import android.app.Application
import com.example.daggerinaction.di.component.AppComponentGraph
import com.example.daggerinaction.di.component.DaggerAppComponentGraph

/**
 * In Android, you usually create a Dagger graph that lives in your application class
 * because you want an instance of the graph to be in memory as long as the app is running.
 * In this way, the graph is attached to the app lifecycle.
 * instantiate DaggerAppComponentGraph generate from the AppComponentGraph interface here in our [Use case] file
 *[AppComponentGraph] lives in the Application class to share its lifecycle but to framework classes,
 * hence we need to also have the application context available in the graph..How do we that?
*/
class App: Application() {
/**Reference to the application graph that is used across the whole app*/
    val appComponentGraph: AppComponentGraph by lazy { DaggerAppComponentGraph.create() }//make sure to rebuild the app before instantiating
}