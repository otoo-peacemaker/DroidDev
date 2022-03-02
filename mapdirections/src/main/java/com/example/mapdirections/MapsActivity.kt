package com.example.mapdirections

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mapdirections.PermissionUtils.isPermissionGranted
import com.example.mapdirections.PermissionUtils.requestPermission
import com.example.mapdirections.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MapsActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {


    private lateinit var map: GoogleMap
    private var permissionDenied = false
    private lateinit var binding: ActivityMapsBinding

    //  private lateinit var currentLocation: Location

     private lateinit var widgetThemeSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiKey = BuildConfig.YOUR_API_KEY
        if (apiKey.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_api_key), Toast.LENGTH_LONG).show()
            return
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        setLaunchActivityClickListener(R.id.autocomplete_button, AutoCompleteActivity::class.java)
        widgetThemeSpinner = findViewById(R.id.theme_spinner)
        widgetThemeSpinner.adapter = ArrayAdapter( /* context= */
            this,
            android.R.layout.simple_list_item_1,
            listOf("Default", "\uD83D\uDCA9 brown", "\uD83E\uDD2E green", "\uD83D\uDE08 purple")
        )


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap
//      val source = LatLng(currentLocation.latitude, currentLocation.longitude)
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        enableMyLocation()
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            requestPermission(
                this, LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
    }


    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Current Location", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(location: Location) {
        val latLang = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions().position(latLang)
        val titleStr = getCityName(location.latitude, location.longitude)
        markerOptions.title(titleStr)
        map.animateCamera(CameraUpdateFactory.newLatLng(latLang))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang, 15f))
        map.addMarker(markerOptions)

        Toast.makeText(
            this,
            "Current location:\n${getCityName(location.latitude, location.longitude)}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getCityName(lat: Double, long: Double): String {
        var cityName: String = ""
        var countryName = ""
        val geoCoder = Geocoder(this, Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 3)

        cityName = address[0].locality
        countryName = address[0].countryName
        Log.d("Debug:", "Your City: $cityName ; your Country $countryName")
        return cityName
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }
        if (isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private fun showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true)
            .show(supportFragmentManager, "dialog")
    }


    private fun setLaunchActivityClickListener(
        @IdRes onClickResId: Int,
        activityClassToLaunch: Class<out AppCompatActivity>
    ) {
        findViewById<FloatingActionButton>(onClickResId).setOnClickListener {
            val intent = Intent(this@MapsActivity, activityClassToLaunch)
            intent.putExtra(THEME_RES_ID_EXTRA, selectedTheme)
            startActivity(intent)
        }


    }

    @get:StyleRes
    private val selectedTheme: Int
        get() {
            return when (widgetThemeSpinner.selectedItemPosition) {
                1 -> R.style.Brown
                2 -> R.style.Green
                3 -> R.style.Purple
                else -> 0
            }
        }

    companion object {
        /**
         * Request code for location permission request.
         *
         * @see .onRequestPermissionsResult
         */
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        const val THEME_RES_ID_EXTRA = "widget_theme"

    }


}