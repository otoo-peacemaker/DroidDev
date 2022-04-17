#Basic Introduction to dependency injection using Dagger
/*
Manual dependency injection or service locators in an Android app can be problematic depending on the size of your project. 
You can limit your project's complexity as it scales up by using <Dagger> to manage dependencies.
Dagger automatically generates code that mimics the code you would otherwise have hand-written. 
Because the code is generated at compile time, it's traceable and more performant than other reflection-based solutions such as <Guice>
*/

###Contents
[1] How to implement Dagger
[2] How to use it in our app


##[1] The implementation

###Entity relation diagram goes like this: ApplicationComponent ->UserRepository->LocalRepo->RemoteRepo
![Use case](https://developer.android.com/images/training/dependency-injection/3-factory-diagram.png?raw=true)
[x] [ApplicationComponent] provides [UserRepository] to everywhere in our app you can use it
[x] [UserRepository] contains the local and remote data source as it's constructor
[x] [ComponentScope] implement custom scope of the component within the app

###The implementation:
import the library <plugins {
  id 'kotlin-kapt'
}

dependencies {
    implementation 'com.google.dagger:dagger:2.x'
    kapt 'com.google.dagger:dagger-compiler:2.x'
}>

###Basic Terms
@Inject: Lets Dagger know how to create instances of the objects in your app
@Component: Makes Dagger create a graph of dependencies in your app
@Singleton: Limit the lifetime of an object to the lifetime of its component. Before you can this annotation,
know the class providers/ that holds instances.
@Scope: To create custom scope

##Using it in our app
This phase would be separate to avoid confusion

