package com.example.weatherapplication.weather_feature.presentation

import com.example.weatherapplication.weather_feature.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)