package com.example.basic.di

import com.example.basic.repository.UserRepository
import dagger.Component

/** This interface provides container for user repository to everywhere in our app
 *
 * @Component tells Dagger to generate a container with all the dependencies required to satisfy the types it exposes.
 * This is called a Dagger component; it contains a graph that consists of the objects that Dagger knows how to provide and their respective dependencies.
 * @ComponentsScope : Tells the limit of its lifetime
 * */


@GraphScope
@Component
interface AppComponentGraph {
    // The return type  of functions inside the component interface is
    // what can be provided from the container
fun userRepo():UserRepository

}