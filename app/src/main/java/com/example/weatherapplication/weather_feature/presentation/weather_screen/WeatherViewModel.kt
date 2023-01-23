package com.example.weatherapplication.weather_feature.presentation.weather_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.weather_feature.domain.location.LocationTracker
import com.example.weatherapplication.weather_feature.domain.repository.WeatherRepository
import com.example.weatherapplication.weather_feature.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
): ViewModel() {

    var state by mutableStateOf(WeatherState())
    private set

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