package com.example.weatherapplication.weather_feature.domain.repository

import com.example.weatherapplication.weather_feature.domain.utils.Place
import com.example.weatherapplication.weather_feature.domain.utils.Resource
import com.example.weatherapplication.weather_feature.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double, place: Place?): Resource<WeatherInfo>
}