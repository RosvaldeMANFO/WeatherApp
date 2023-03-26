package com.example.weatherapplication.weather_feature.domain.weather

import com.example.weatherapplication.weather_feature.domain.utils.Place

data class WeatherInfo(
    val weatherDataPearDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?,
    val place: Place?
)