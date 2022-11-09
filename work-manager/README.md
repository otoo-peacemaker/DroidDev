WorkManager Code-lab
===================================

Pre-requisites
--------------

* Android Studio 3.6 or later and you know how to use it.

* Make sure Android Studio is updated, as well as your SDK and Gradle.
Otherwise, you may have to wait for a while until all the updates are done.

* A device or emulator that runs API level 16+

You need to be solidly familiar with the Kotlin programming language,
object-oriented design concepts, and Android Development Fundamentals.
In particular:

* Basic layouts, widgets and [View Bindings](https://d.android.com/topic/libraries/view-binding)
* Some familiarity with Uris and File I/O
* Familiarity with [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
  and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

Getting Started
---------------

1. [Install Android Studio](https://developer.android.com/studio/install.html),
if you don't already have it.
2. Download the sample.
2. Import the sample into Android Studio.
3. Build and run the sample.



What's WorkManager
------------------
WorkManager is part of Android Jetpack and an Architecture Component for background work that needs a combination of **opportunistic** and **guaranteed execution**. 

Opportunistic execution means that __WorkManager will do your background work as soon as it can__. 

Guaranteed execution means that WorkManager will __take care of the logic to start your work under a variety of situations__, even if you navigate away from your app.


* WorkManager is a backwards compatible(it can trace back to API 14)
* flexible and simple library for deferrable background work(Deferrable work is a task that can be run later and still be useful.)
* (Deferred tasks/work Every task that is not directly connected to a user interaction and can run at any time in the future can be deferred). 
* WorkManager is the recommended task scheduler on Android for deferrable work, with a guarantee to be executed.
* WorkManager sits on top of a few APIs such as **JobScheduler** and **AlarmManager**. 
* WorkManager picks the right APIs to use, based on conditions like the user's device API level.

Benefits of using WorkManager
----------------------------
* Support for both asynchronous one-off and periodic tasks 
* Support for constraints such as network conditions, storage space, and charging status 
* Chaining of complex work requests, including running work in parallel 
* Output from one work request used as input for the next 
* Handling API level compatibility back to API level 14
* Working with or without Google Play services 
* Following system health best practices 
* LiveData support to easily display work request state in UI

When to use WorkManager
-----------------------
The WorkManager library is a good choice for tasks that are useful to complete, even if the user navigates away from the particular screen or your app.

Some examples of tasks that are a good use of WorkManager:

* Uploading logs
* Applying filters to images and saving the image
* Periodically syncing local data with the network
WorkManager offers guaranteed execution, and not all tasks require that.
As such, it is not a catch-all for running every task off of the main thread.

What you'll learn
-----------------
* Adding WorkManager to your project 
* Scheduling a simple task 
* Input and output parameters
* Chaining work
* Unique work 
* Displaying work status in the UI 
* Cancelling work 
* Work constraints

WorkManager Basics
------------------
There are a few WorkManager classes you need to know about:

* **Worker**: This is where you put the code for the actual work you want to perform in the background. 
  * You'll extend this class and override the **doWork()** method.
  
* **WorkRequest**: This represents a request to do some work. 
  * You'll pass in your Worker as part of creating your WorkRequest. 
  * When making the WorkRequest you can also specify things like Constraints on when the Worker should run.
  
* **WorkManager**: This class actually schedules your WorkRequest and makes it run. 
  * It schedules WorkRequests in a way that spreads out the load on system resources, while honoring the constraints you specify.


Make your first WorkRequest
---------------------------
Step 1 - Make BlurWorker class and let it extend Worker

Step 2 - Add a constructor(ctx, params)

Step 3 - Override and implement doWork()

  completed code: steps 1-3

```
package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.R

private const val TAG = "BlurWorker"
class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        //Get a Context by calling the applicationContext property. Assign it to a new val named appContext
        val appContext = applicationContext

        //Display a status notification using the function, makeStatusNotification to notify the user about blurring the image
        makeStatusNotification("Blurring image", appContext)

        return try {
            //Create a Bitmap from the cupcake image
            val picture = BitmapFactory.decodeResource(
                appContext.resources,
                R.drawable.android_cupcake)


            //Get a blurred version of the bitmap by calling the blurBitmap method from WorkerUtils.
            val output = blurBitmap(picture, appContext)

            // Write bitmap to a temp file(save it to temporal file)
            val outputUri = writeBitmapToFile(appContext, output)


            //Make a Notification displaying the URI by calling the makeStatusNotification method from WorkerUtils.
            makeStatusNotification("Output is $outputUri", appContext)

            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error applying blur")
            Result.failure()
        }
    }
}
```

Step 4 - Get WorkManager in the ViewModel
 `private val workManager = WorkManager.getInstance(application)`

Step 5 - Enqueue the WorkRequest in WorkManager

Alright, time to make a WorkRequest and tell WorkManager to run it(BlurWorker). 
There are two types of WorkRequests:
* OneTimeWorkRequest: A WorkRequest that will only execute once.
* PeriodicWorkRequest: A WorkRequest that will repeat on a cycle.
In our case we're gon use the OneTimeWorkRequest because we wanna blur the image once
`workManager.enqueue(OneTimeWorkRequest.from(BlurWorker::class.java))`


Chain your Work
-----------------
WorkManager allows you to create separate WorkerRequests that run in order or parallel. In this step you'll create a chain of work that looks like this

////////////////////image

Step 1 - Create Cleanup and Save Workers

First, you'll define all the Worker classes you need. 
You already have a Worker for blurring an image, but you also need a Worker which cleans up temp files and a Worker which saves the image permanently.
Create two new classes in the workers package which extend Worker.
The first should be called **CleanupWorker**, the second should be called **SaveImageToFileWorker**.

Step 2 - Make it extend Worker

Extend CleanupWorker class from Worker class. Add the required constructor parameters.
class CleanupWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {}

Step 3 - Override and implement doWork() for CleanupWorker

CleanupWorker doesn't need to take any input or pass any output. It always deletes the temporary files if they exist

code:
```
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.OUTPUT_PATH
import java.io.File

/**
* Cleans up temporary files generated during blurring process
  */
  private const val TAG = "CleanupWorker"
  class CleanupWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

  override fun doWork(): Result {
  // Makes a notification when the work starts and slows down the work so that
  // it's easier to see each WorkRequest start, even on emulated devices
  makeStatusNotification("Cleaning up old temporary files", applicationContext)
  sleep()

       return try {
           val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)
           if (outputDirectory.exists()) {
               val entries = outputDirectory.listFiles()
               if (entries != null) {
                   for (entry in entries) {
                       val name = entry.name
                       if (name.isNotEmpty() && name.endsWith(".png")) {
                           val deleted = entry.delete()
                           Log.i(TAG, "Deleted $name - $deleted")
                       }
                   }
               }
           }
           Result.success()
       } catch (exception: Exception) {
           exception.printStackTrace()
           Result.failure()
       }
  }
  }
```


Step 4 - Override and implement doWork() for SaveImageToFileWorker

SaveImageToFileWorker will take input and output. 
The input is a String of the temporarily blurred image URI stored with the key KEY_IMAGE_URI. 
And the output will also be a String, the URI of the saved blurred image stored with the key KEY_IMAGE_URI.

/////////////////image



Notice how the resourceUri and output values are retrieved with the key KEY_IMAGE_URI. 
This is very similar to the code you wrote in the last step for input and output (it uses all the same keys).

code:

```
package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.work.workDataOf
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.KEY_IMAGE_URI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Saves the image to a permanent file
 */
private const val TAG = "SaveImageToFileWorker"
class SaveImageToFileWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val title = "Blurred Image"
    private val dateFormatter = SimpleDateFormat(
            "yyyy.MM.dd 'at' HH:mm:ss z",
            Locale.getDefault()
    )

    override fun doWork(): Result {
        // Makes a notification when the work starts and slows down the work so that
        // it's easier to see each WorkRequest start, even on emulated devices
        makeStatusNotification("Saving image", applicationContext)
        sleep()

        val resolver = applicationContext.contentResolver
        return try {
            val resourceUri = inputData.getString(KEY_IMAGE_URI)
            val bitmap = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri)))
            val imageUrl = MediaStore.Images.Media.insertImage(
                    resolver, bitmap, title, dateFormatter.format(Date()))
            if (!imageUrl.isNullOrEmpty()) {
                val output = workDataOf(KEY_IMAGE_URI to imageUrl)

                Result.success(output)
            } else {
                Log.e(TAG, "Writing to MediaStore failed")
                Result.failure()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure()
        }
    }
}
```


Step 5 - Modify BlurWorker Notification

Now that we have a chain of Workers taking care of saving the image in the correct folder, 
we can slow down the work by using the sleep() method defined in the WorkerUtils class, 
so that it's easier to see each WorkRequest start, even on emulated devices. 
The final version of BlurWorker become

```
class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

override fun doWork(): Result {
    val appContext = applicationContext

    val resourceUri = inputData.getString(KEY_IMAGE_URI)

    makeStatusNotification("Blurring image", appContext)

    // ADD THIS TO SLOW DOWN THE WORKER
    sleep() 
    // ^^^^
    
    return try {
        if (TextUtils.isEmpty(resourceUri)) {
            Timber.e("Invalid input uri")
            throw IllegalArgumentException("Invalid input uri")
        }

        val resolver = appContext.contentResolver

        val picture = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri)))

        val output = blurBitmap(picture, appContext)

        // Write bitmap to a temp file
        val outputUri = writeBitmapToFile(appContext, output)

        val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())

        Result.success(outputData)
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        Result.failure()
    }
}

```


Step 6 - Create a WorkRequest chain

You need to modify the BlurViewModel's applyBlur method to execute a chain of WorkRequests instead of just one. 
Currently the code looks like this:
```
val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
        .setInputData(createInputDataForUri())
        .build()

workManager.enqueue(blurRequest)

```

Instead of calling workManager.enqueue(), call workManager.beginWith(). 
This returns a WorkContinuation, which defines a chain of WorkRequests. 
You can add to this chain of work requests by calling then() method, 
for example, if you have three WorkRequest objects, workA, workB, and workC, you could do the following:

```
// Example code, don't copy to the project
val continuation = workManager.beginWith(workA)

continuation.then(workB) // FYI, then() returns a new WorkContinuation instance
        .then(workC)
        .enqueue() // Enqueues the WorkContinuation which is a chain of work 
```
This would produce and run the following chain of WorkRequests:

/////////////////image


* Create a chain of a CleanupWorker WorkRequest, 
* a BlurImage WorkRequest and a SaveImageToFile WorkRequest in applyBlur. 
* Pass input into the BlurImage WorkRequest

code:

```
internal fun applyBlur(blurLevel: Int) {
    // Add WorkRequest to Cleanup temporary images
    var continuation = workManager
            .beginWith(OneTimeWorkRequest
            .from(CleanupWorker::class.java))

    // Add WorkRequest to blur the image
    val blurRequest = OneTimeWorkRequest.Builder(BlurWorker::class.java)
            .setInputData(createInputDataForUri())
            .build()

    continuation = continuation.then(blurRequest)
    
    // Add WorkRequest to save the image to the filesystem
    val save = OneTimeWorkRequest.Builder(SaveImageToFileWorker::class.java).build()

    continuation = continuation.then(save)

    // Actually start the work
    continuation.enqueue()
}
```


Step 7 - Repeat the BlurWorker

Time to add the ability to blur the image by different amounts. 
Take the blurLevel parameter passed into applyBlur and add that many blur WorkRequest operations to the chain.
Only the first WorkRequest needs and should take in the uri input


Ensure unique work
-------------------
Now that you've used chains, it's time to tackle another powerful feature of WorkManager - **unique work chains**.

Sometimes you only want one chain of work to run at a time. 
For example, perhaps you have a work chain that syncs your local data with the server 
- you probably want to let the first data sync finish before starting a new one. 
- To do this, you would use beginUniqueWork instead of beginWith; and you provide a unique String name. 
- This names the entire chain of work requests so that you can refer to and query them together.

Ensure that your chain of work to blur your file is unique by using **beginUniqueWork**. Pass in IMAGE_MANIPULATION_WORK_NAME as the key. 
You'll also need to pass in a **ExistingWorkPolicy**. Your options are **REPLACE**, **KEEP** or **APPEND**.

You'll use REPLACE because if the user decides to blur another image before the current one is finished, we want to stop the current one and start blurring the new image.

The code for starting your unique work continuation is below:

```
// REPLACE THIS CODE:
// var continuation = workManager
//            .beginWith(OneTimeWorkRequest
//            .from(CleanupWorker::class.java))
// WITH
var continuation = workManager
        .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
        )

```

Tag and display Work status
---------------------------
The next big change you'll do is to actually change what's showing in the app as the Work executes.

You can get the status of any WorkRequest by getting a LiveData that holds a WorkInfo object. 
WorkInfo is an object that contains details about the current state of a WorkRequest, including:

* Whether the work is **BLOCKED**, **CANCELLED**, **ENQUEUED**, **FAILED**, **RUNNING** or **SUCCEEDED**.
* If the WorkRequest is finished, any output data from the work.

- You'll be tagging the SaveImageToFileWorker WorkRequest, so that you can get it using getWorkInfosByTag. 
- You'll use a tag to label your work instead of using the WorkManager ID, because if your user blurs multiple images, 
- all of the saving image WorkRequests will have the same tag but not the same ID. 
- Also you are able to pick the tag.

- You would not use getWorkInfosForUniqueWork because that would return the WorkInfo for all of the blur WorkRequests and 
- the cleanup WorkRequest as well; it would take extra logic to find the save image WorkRequest.

Step 1 - Tag your work

In applyBlur, when creating the SaveImageToFileWorker, tag your work using the String constant TAG_OUTPUT :

```
val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
        .addTag(TAG_OUTPUT) // <-- ADD THIS
        .build()
        
```

Step 2 - Get the WorkInfo

Now that you've tagged the work, you can get the WorkInfo:
* In BlurViewModel, declare a new class variable called outputWorkInfos which is a LiveData<List<WorkInfo>>
* In BlurViewModel add an init block to get the WorkInfo using WorkManager.getWorkInfosByTagLiveData

The code you need is below:

```
// New instance variable for the WorkInfo
internal val outputWorkInfos: LiveData<List<WorkInfo>>

// Modify the existing init block in the BlurViewModel class to this:
init {
    imageUri = getImageUri(application.applicationContext)
    // This transformation makes sure that whenever the current work Id changes the WorkInfo
    // the UI is listening to changes
    outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
} 

```

Step 3 - Display the WorkInfo

Now that you have a LiveData for your WorkInfo, you can observe it in the BlurActivity. In the observer:

* Check if the list of WorkInfo is not null and if it has any WorkInfo objects in it - if not then the Go button has not been clicked yet, so return.
* Get the first WorkInfo in the list; there will only ever be one WorkInfo tagged with **TAG_OUTPUT** because we made the chain of work unique.
* Check whether the work status is finished, using **workInfo.state.isFinished**.
* If it's not finished, then call **showWorkInProgress()** which hides the Go button and shows the Cancel Work button and progress bar.
* If it's finished then call **showWorkFinished()** which hides the Cancel Work button and progress bar and displays the Go button.

code:

```agsl
override fun onCreate(savedInstanceState: Bundle?) {
    ...
    // Observe work status, added in onCreate()
    viewModel.outputWorkInfos.observe(this, workInfosObserver())
}

// Define the observer function
private fun workInfosObserver(): Observer<List<WorkInfo>> {
    return Observer { listOfWorkInfo ->

        // Note that these next few lines grab a single WorkInfo if it exists
        // This code could be in a Transformation in the ViewModel; they are included here
        // so that the entire process of displaying a WorkInfo is in one location.

        // If there are no matching work info, do nothing
        if (listOfWorkInfo.isNullOrEmpty()) {
            return@Observer
        }

        // We only care about the one output status.
        // Every continuation has only one worker tagged TAG_OUTPUT
        val workInfo = listOfWorkInfo[0]

        if (workInfo.state.isFinished) {
            showWorkFinished()
        } else {
            showWorkInProgress()
        }
    }
}
```

Show final output
-----------------
Each WorkInfo also has a getOutputData method which allows you to get the output Data object with the final saved image. 
In Kotlin you can access this method using a variable that the language generates for you: outputData. 
Let's display a button that says See File whenever there's a blurred image ready to show.

Step 1 - Create the ‘See File' button

There's already a button in the activity_blur.xml layout that is hidden. It's in BlurActivity and called outputButton.

BlurActivity.kt:

```agsl
override fun onCreate(savedInstanceState: Bundle?) {
   // Setup view output image file button
   binding.seeFileButton.setOnClickListener {
       viewModel.outputUri?.let { currentUri ->
           val actionView = Intent(Intent.ACTION_VIEW, currentUri)
           actionView.resolveActivity(packageManager)?.run {
               startActivity(actionView)
           }
       }
   }
}
```


Step 2 - Set the URI and show the button

There are a few final tweaks you need to apply to the WorkInfo observer to get this to work (no pun intended):

* If the WorkInfo is finished, get the output data, using **workInfo.outputData**.
* Then get the output URI, remember that it's stored with the **Constants.KEY_IMAGE_URI key**.
* Then if the URI isn't empty, it is saved properly; show the outputButton and call setOutputUri on the view model with the uri.
  
BlurActivity.kt

```agsl
private fun workInfoObserver(): Observer<List<WorkInfo>> {
    return Observer { listOfWorkInfo ->

        // Note that these next few lines grab a single WorkInfo if it exists
        // This code could be in a Transformation in the ViewModel; they are included here
        // so that the entire process of displaying a WorkInfo is in one location.

        // If there are no matching work info, do nothing
        if (listOfWorkInfo.isNullOrEmpty()) {
            return@Observer
        }

        // We only care about the one output status.
        // Every continuation has only one worker tagged TAG_OUTPUT
        val workInfo = listOfWorkInfo[0]

        if (workInfo.state.isFinished) {
            showWorkFinished()

            // Normally this processing, which is not directly related to drawing views on
            // screen would be in the ViewModel. For simplicity we are keeping it here.
            val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)

            // If there is an output file show "See File" button
            if (!outputImageUri.isNullOrEmpty()) {
                viewModel.setOutputUri(outputImageUri)
                binding.seeFileButton.visibility = View.VISIBLE
            }
        } else {
            showWorkInProgress()
        }
    }
}
```

Cancel work
--------------
You added this Cancel Work button, so let's add the code to make it do something. 
With WorkManager, you can cancel work using the id, by tag and by unique chain name.

Step 1 - Cancel the work by name

In BlurViewModel, add a new method called cancelWork() to cancel the unique work. 
Inside the function call cancelUniqueWork on the workManager, pass in the tag IMAGE_MANIPULATION_WORK_NAME.

BlurViewModel.kt:

```agsl
internal fun cancelWork() {
    workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
}
```

Step 2 - Call cancel method

BlurActivity.kt

```agsl
// In onCreate()
// Hookup the Cancel button
binding.cancelButton.setOnClickListener { viewModel.cancelWork() }
```


Work constraints
-----------------

Last but not least, WorkManager supports Constraints. 
For Blur-O-Matic, you'll use the constraint that the device must be charging. 
This means that your work request will only run if the device is charging.

Step 1 - Create and add charging constraint

To create a Constraints object, you use a Constraints.Builder. 
hen you set the constraints you want and add it to the WorkRequest using the method, setRequiresCharging() as shown below:

`Import androidx.work.Constraints when requested.`

BlurViewModel.kt

```agsl
// Put this inside the applyBlur() function, above the save work request.
// Create charging constraint
val constraints = Constraints.Builder()
        .setRequiresCharging(true)
        .build()

// Add WorkRequest to save the image to the filesystem
val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
        .setConstraints(constraints)
        .addTag(TAG_OUTPUT)
        .build()
continuation = continuation.then(save)

// Actually start the work
continuation.enqueue()
```











Materials
----------------------------

This repository contains the code for the
[WorkManager Codelab](https://codelabs.developers.google.com/codelabs/android-workmanager):

Java version
--------------

The Java version of this codelab is available under the
[`Java`](https://github.com/googlecodelabs/android-workmanager/tree/java) branch of this
repository.

Introduction
------------

At I/O 2018 Google announced [Android Jetpack](https://developer.android.com//jetpack/),
a collection of libraries, tools and architectural guidance to accelerate and simplify the
development of great Android apps. One of those libraries is the
[WorkManager library](https://developer.android.com/topic/libraries/architecture/workmanager/).
The WorkManager library provides a unified API for deferrable one-off or recurring background tasks
that need guaranteed execution. You can learn more by reading the
[WorkManager Guide](https://developer.android.com/topic/libraries/architecture/workmanager/), the
[WorkManager Reference](https://developer.android.com/reference/androidx/work/package-summary)
or doing the
[WorkManager Codelab](https://codelabs.developers.google.com/codelabs/android-workmanager).



License
-------

Copyright 2018 Google, Inc.

All image and audio files (including *.png, *.jpg, *.svg, *.mp3, *.wav
and *.ogg) are licensed under the CC BY 4.0 license. All other files are
licensed under the Apache 2 license.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the LICENSE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
