package com.example.weatherapplication.weather_feature.presentation.util

sealed class Screen(val route: String) {
    object WeatherScreen: Screen("weather_screen")
    object FindLocationScreen: Screen("find_location")
}