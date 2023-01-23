package com.example.weatherapplication.weather_feature.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherapplication.weather_feature.data.data_source.WeatherApi
import com.example.weatherapplication.weather_feature.data.mappers.toWeatherInfo
import com.example.weatherapplication.weather_feature.domain.repository.WeatherRepository
import com.example.weatherapplication.weather_feature.domain.utils.Resource
import com.example.weatherapplication.weather_feature.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try{
            Resource.Success(
                data = api.getWeatherData(
                    lat,
                    long
                ).toWeatherInfo()
            )
        } catch (e: Exception){
            e.printStackTrace()
            Log.e("Exception", "${e.message}")
            Resource.Error(e.message?:"An unknown error occurred.")
        }
    }
}