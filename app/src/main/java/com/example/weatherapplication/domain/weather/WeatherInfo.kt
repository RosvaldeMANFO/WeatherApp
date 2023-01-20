package com.example.weatherapplication.domain.weather

data class WeatherInfo(
    val weatherDataPearDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?
)