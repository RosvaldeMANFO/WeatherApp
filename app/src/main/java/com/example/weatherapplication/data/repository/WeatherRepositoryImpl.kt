package com.example.weatherapplication.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapplication.data.data_source.WeatherApi
import com.example.weatherapplication.data.mappers.toWeatherInfo
import com.example.weatherapplication.domain.repository.WeatherRepository
import com.example.weatherapplication.domain.utils.Resource
import com.example.weatherapplication.domain.weather.WeatherInfo
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
            Resource.Error(e.message?:"An unknown error occurred.")
        }
    }
}