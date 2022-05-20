package com.example.locationservice.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationservice.databinding.ActivityLocationBinding
import com.example.locationservice.di.App
import com.example.locationservice.model.LocationInfo
import com.example.locationservice.ui.adapter.LocationAdapter
import com.example.locationservice.ui.viewmodel.ActivityViewModel
import com.example.locationservice.ui.viewmodel.ViewModelFactory

class LocationActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityLocationBinding
    private val binding get() = _binding

    private val viewModel: ActivityViewModel by viewModels {
        ViewModelFactory((application as App).repository)
    }

    private val locationAdapter: LocationAdapter by lazy { LocationAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = locationAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val getData = intent.getParcelableExtra<LocationInfo>("location")
        getData?.let {
            val list = ArrayList<LocationInfo>()
            list.add(LocationInfo(it.lat,it.long, it.currentTime))
            locationAdapter.submitData(list)
            Log.i("LOCATION",".................................${locationAdapter.submitData(list)}")
        }

       /* this.lifecycleScope.launchWhenStarted {
            viewModel.getDetails.observe(this@LocationActivity, Observer {
                it?.let {
                    locationAdapter.submitData(it)
                }
            })
        }*/

    }

}