package com.example.locationservice.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.locationservice.databinding.ActivityMainBinding
import com.example.locationservice.di.App
import com.example.locationservice.model.LocationDetails
import com.example.locationservice.ui.viewmodel.MainActivityViewModel
import com.example.locationservice.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels {
        ViewModelFactory((application as App).repository)
    }

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startService.setOnClickListener {
            this.lifecycleScope.launch {
                this@MainActivity.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    service()
                }
            }
        }

        binding.viewService.setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }
    }

    private fun service() {
        val currentDate = Calendar.getInstance().time.toString()
        val longitude = 6.0969 ; val latitude = 4.9966

        val list = listOf(latitude, longitude, currentDate)
        val details = LocationDetails(
            longitude = longitude,
            latitude = latitude,
            date = currentDate
        )


        lifecycleScope.launch {
            viewModel.insertIntoRoom(
                details
            )
        }


        Log.i(
            "TAG", ".............................: ${
                viewModel.insertIntoRoom(
                    details
                )
            }"
        )

        Log.i(
            "TAG", ".............................: $details"
        )
    }

    companion object {
        const val EXTRA_INFO = "DETAILS"
        const val TAG = "MAIN ACTIVITY"
    }
}