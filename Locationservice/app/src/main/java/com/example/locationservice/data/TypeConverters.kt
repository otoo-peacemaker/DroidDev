package com.example.locationservice.data

import androidx.room.TypeConverter
import com.example.locationservice.model.LocationDetails
import com.google.gson.Gson

class TypeConverters {
    @TypeConverter
    fun locationDetailsListToJson(value: List<LocationDetails>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromJsonToLocationDetails(value: String): LocationDetails {
        return Gson().fromJson(value, LocationDetails::class.java)
    }
}