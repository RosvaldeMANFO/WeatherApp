package com.example.weatherapplication.weather_feature.domain.weather

data class WeatherInfo(
    val weatherDataPearDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?
)