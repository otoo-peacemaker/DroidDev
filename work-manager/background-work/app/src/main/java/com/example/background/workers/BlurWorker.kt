package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.R
import android.util.Log
import androidx.work.workDataOf
import com.example.background.KEY_IMAGE_URI


private const val TAG = "BlurWorker"
class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        //Get a Context by calling the applicationContext property. Assign it to a new val named appContext
        val appContext = applicationContext

        val resourceUri = inputData.getString(KEY_IMAGE_URI)

        //Display a status notification using the function, makeStatusNotification to notify the user about blurring the image
        makeStatusNotification("Blurring image", appContext)

        // ADD THIS TO SLOW DOWN THE WORKER
        sleep()

        return try {
            //Create a Bitmap from the cupcake image
            //val picture = BitmapFactory.decodeResource(appContext.resources, R.drawable.android_cupcake)

            if (TextUtils.isEmpty(resourceUri)) {
                Log.e(TAG, "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }

            val resolver = appContext.contentResolver

            val picture = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))

            //Get a blurred version of the bitmap by calling the blurBitmap method from WorkerUtils.
            val output = blurBitmap(picture, appContext)

            // Write bitmap to a temp file(save it to temporal file)
            val outputUri = writeBitmapToFile(appContext, output)


            //Make a Notification displaying the URI by calling the makeStatusNotification method from WorkerUtils.
            makeStatusNotification("Output is $outputUri", appContext)

            //Output temporary URI
            //Provide the Output URI as an output Data to make this temporary image easily accessible to other workers for further operations.
            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())

           // Result.success()
            Result.success(outputData)//passing the output to the worker result.
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error applying blur")
            Result.failure()
        }
    }



}