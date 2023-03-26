package com.example.weatherapplication.weather_feature.domain.location

import android.location.Location
import com.example.weatherapplication.weather_feature.domain.utils.Place

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
    suspend fun getCurrentPlace(location: Location): Place?
}