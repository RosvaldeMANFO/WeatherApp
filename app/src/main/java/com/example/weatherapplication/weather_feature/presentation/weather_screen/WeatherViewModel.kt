package com.example.weatherapplication.weather_feature.presentation.weather_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.weather_feature.domain.location.LocationTracker
import com.example.weatherapplication.weather_feature.domain.repository.WeatherRepository
import com.example.weatherapplication.weather_feature.domain.use_case.OrderWeekWeatherUseCase
import com.example.weatherapplication.weather_feature.domain.utils.OrderType
import com.example.weatherapplication.weather_feature.domain.utils.Resource
import com.example.weatherapplication.weather_feature.domain.utils.WeatherOrder
import com.example.weatherapplication.weather_feature.domain.weather.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
): ViewModel() {

    var state by mutableStateOf(WeatherState())
    private set

    fun onEvent(event:  WeatherEvents){
        state = when(event){
            is WeatherEvents.ChangeHourlyWeather ->{
                state.copy(
                    weatherInfo = state.weatherInfo?.copy(currentWeatherData = event.weather)
                )
            }
            is WeatherEvents.FilterWeekWeather ->{
                val newWeatherPearDayOrder = state.weatherInfo?.weatherDataPearDay?.let {
                    OrderWeekWeatherUseCase().invoke(it, event.weatherOrder)
                }
                newWeatherPearDayOrder?.let {
                    state.copy(
                        weatherInfo = state.weatherInfo?.copy(weatherDataPearDay = it)
                    )
                }?: state
            }
            is WeatherEvents.ChangeDay ->{
                val currentWeatherData = state.weatherInfo?.weatherDataPearDay?.get(event.index)
                val now = LocalDateTime.now()
                currentWeatherData?.let {
                    state.copy(
                        state.weatherInfo?.weatherDataPearDay?.let { weatherPearDay ->
                            WeatherInfo(
                                weatherDataPearDay = weatherPearDay,
                                currentWeatherData = currentWeatherData.find {
                                   val hour =  if(now.minute < 30) now.hour else now.hour + 1
                                    it.time.hour == hour
                                }
                            )
                        }
                    )
                }?: state
            }
        }
    }

    fun loadWeatherInfo(){
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when(val result = repository.getWeatherData(location.latitude, location.longitude)){
                    is Resource.Success ->{
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null,
                        )
                    }
                    is Resource.Error ->{
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable gps"
                )
            }
        }
    }
}