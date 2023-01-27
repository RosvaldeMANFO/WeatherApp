package com.example.weatherapplication.weather_feature.presentation.weather_screen

import com.example.weatherapplication.weather_feature.domain.utils.WeatherOrder
import com.example.weatherapplication.weather_feature.domain.weather.WeatherData

sealed class WeatherEvents {
    data class ChangeHourlyWeather(val weather: WeatherData): WeatherEvents()
    data class FilterWeekWeather(val weatherOrder: WeatherOrder): WeatherEvents()
    data class ChangeDay(val index: Int): WeatherEvents()
}