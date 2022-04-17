package com.example.daggerinaction.di.component

import com.example.daggerinaction.di.Scope
import com.example.daggerinaction.di.module.NetworkModule
import com.example.daggerinaction.repository.UserRepository
import com.example.daggerinaction.ui.login.LoginFragment
import com.example.daggerinaction.ui.register.RegisterFragment
import dagger.Component

/**
 *The "modules" attribute in the @Component annotation tells Dagger what
 * Modules to include when building the graph
 * */

@Scope
@Component(modules = [NetworkModule::class])
interface AppComponentGraph {

    fun repository(): UserRepository


    /**
     * This tells Dagger that Fragments requests injection so the graph needs to
     * satisfy all the dependencies of the fields that Fragment is requesting[View Models].
     * */
    fun injectLogin(loginFragment: LoginFragment)
    fun injectRegistration(registerFragment: RegisterFragment)
}