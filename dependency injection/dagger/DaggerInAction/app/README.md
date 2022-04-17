#This module talks about how to use Dagger in our android app
First, let's import the dependencies in the build.gradle file[module not the app]
plugins {
  id 'kotlin-kapt'
}

dependencies {
    implementation 'com.google.dagger:dagger:2.x'
    kapt 'com.google.dagger:dagger-compiler:2.x'
}


![Use case](https://developer.android.com/images/training/dependency-injection/4-application-graph.png?raw=true)
