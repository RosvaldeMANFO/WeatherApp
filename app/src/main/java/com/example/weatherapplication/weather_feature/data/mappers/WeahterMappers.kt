package com.example.weatherapplication.weather_feature.data.mappers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherapplication.weather_feature.data.data_source.WeatherDataDto
import com.example.weatherapplication.weather_feature.data.data_source.WeatherDto
import com.example.weatherapplication.weather_feature.domain.utils.Place
import com.example.weatherapplication.weather_feature.domain.weather.IndexWeatherData
import com.example.weatherapplication.weather_feature.domain.weather.WeatherData
import com.example.weatherapplication.weather_feature.domain.weather.WeatherInfo
import com.example.weatherapplication.weather_feature.domain.weather.WeatherType
import com.google.gson.Gson
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>>{
    return time.mapIndexed{index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
      it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDto.toWeatherInfo(place: Place?): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find{
        val hour = if(now.minute < 30) now.hour else now.hour +1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPearDay = weatherDataMap,
        currentWeatherData = currentWeatherData,
        place = place
    )
}