package com.example.weatherapplication.data.data_source

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast?current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long:  Double
    ): WeatherDto
}