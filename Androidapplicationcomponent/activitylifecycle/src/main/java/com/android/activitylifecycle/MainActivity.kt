package com.android.activitylifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.activitylifecycle.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    /**Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI,
     * using findViewById to programmatically interact with widgets in the UI*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val current = Calendar.getInstance().time.toString()

        this.lifecycleScope.launchWhenCreated {
            Log.d(TAG, "Activity on OnCREATE method inside COROUTINES.................: \n${current.drop(9)}")
            delay(1000)
            Log.d(TAG, "Activity on coroutine finished............. : \n${current.drop(9)}")
        }
        //Log.d(TAG, "Activity on OnCREATE method after routine : \n${current.drop(9)}")

        binding.nextScreen.setOnClickListener {
            startActivity(Intent(this, MainActivity3::class.java))
        }

    }


    /**Called after onCreate — or after onRestart when the activity had been stopped,
     * but is now again being displayed to the user. It will usually be followed by onResume.
     * This is a good place to begin drawing visual elements, running animations, etc.*/

    override fun onStart() {
        super.onStart()
        val current = Calendar.getInstance().timeInMillis.toString()
        Log.d(TAG, "Activity on START method : \n${current.drop(9)}")
    }

    /**
     * Called after onRestoreInstanceState, onRestart, or onPause.
     * This is usually a hint for your activity to start interacting with the user,
     * which is a good indicator that the activity became active and ready to receive input.
     * */

    override fun onResume() {
        super.onResume()
        val current = Calendar.getInstance().timeInMillis.toString()
        Log.d(TAG, "Activity on RESUME method : \n${current.drop(9)}")
    }

    /**Called as part of the activity lifecycle when the user no longer actively interacts with the activity,
     * but it is still visible on screen. The counterpart to onResume.*/
    override fun onPause() {
        super.onPause()
        val current = Calendar.getInstance().timeInMillis.toString()
        Log.d(TAG, "Activity on PAUSE method : \n${current.drop(9)}")
    }

    /**Called after onStop when the current activity is being re-displayed
     * to the user (the user has navigated back to it).
     * It will be followed by onStart and then onResume.*/

    override fun onRestart() {
        super.onRestart()
        val current = Calendar.getInstance().timeInMillis.toString()
        Log.d(TAG, "Activity on OnRESTART method : \n${current.drop(9)}")
    }


    /**Called when you are no longer visible to the user.
     * You will next receive either onRestart, onDestroy, or nothing, depending on later user activity*/

    override fun onStop() {
        super.onStop()
        val current = Calendar.getInstance().timeInMillis.toString()
        Log.d(TAG, "Activity on STOP method : \n${current.drop(9)}")
    }

    /**Perform any final cleanup before an activity is destroyed.
     * This can happen either because the activity is finishing (someone called finish on it), or
     * because the system is temporarily destroying this instance of the activity to save space.
     * You can distinguish between these two scenarios with the isFinishing method.*/

    override fun onDestroy() {
        super.onDestroy()
        val current = Calendar.getInstance().timeInMillis.toString()
        Log.d(TAG, "Activity on DESTROY method : \n${current.drop(9)}")
    }


    companion object {
        const val TAG = "MAIN ACTIVITY"
    }
}