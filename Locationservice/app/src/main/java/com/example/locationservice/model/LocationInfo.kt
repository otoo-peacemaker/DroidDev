package com.example.locationservice.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationInfo(
    val lat: Double,
    val long:Double,
    val currentTime: String
) : Parcelable
