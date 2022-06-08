package com.android.activitylifecycle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.activitylifecycle.databinding.ActivityMain2Binding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
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
        const val TAG = "MAIN ACTIVITY2"
    }
}