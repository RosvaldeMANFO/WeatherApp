package com.example.weatherapplication.weather_feature.data.data_source

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?current_weather=true&hourly=temperature_2m,pressure_msl,weathercode,relativehumidity_2m,windspeed_10m")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long:  Double
    ): WeatherDto
}