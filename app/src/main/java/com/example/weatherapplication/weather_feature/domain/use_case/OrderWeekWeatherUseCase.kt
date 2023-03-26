package com.example.weatherapplication.weather_feature.domain.use_case

import com.example.weatherapplication.weather_feature.domain.utils.OrderType
import com.example.weatherapplication.weather_feature.domain.utils.WeatherOrder
import com.example.weatherapplication.weather_feature.domain.weather.IndexWeatherData
import com.example.weatherapplication.weather_feature.domain.weather.WeatherData
import com.example.weatherapplication.weather_feature.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class OrderWeekWeatherUseCase {
    operator fun invoke(
        weathers: Map<Int, List<WeatherData>>,
        weatherOrder: WeatherOrder = WeatherOrder.TemperatureCelsius(OrderType.Ascending)
    ): Map<Int, List<WeatherData>> {
        return when(weatherOrder.orderType){
            is OrderType.Ascending ->{
                when(weatherOrder){
                    is WeatherOrder.TemperatureCelsius -> weathers.values.sortedBy { day ->
                        day.map { weather -> weather.temperatureCelsius }.average()
                    }
                    is WeatherOrder.Pressure -> weathers.values.sortedBy { day ->
                        day.map { weather -> weather.pressure }.average()
                    }
                    is WeatherOrder.Humidity -> weathers.values.sortedBy { day ->
                        day.map { weather -> weather.humidity }.average()
                    }
                }
            }
            is OrderType.Descending ->{
                when(weatherOrder){
                    is WeatherOrder.TemperatureCelsius -> weathers.values.sortedByDescending { day ->
                        day.map { weather -> weather.temperatureCelsius }.average()
                    }
                    is WeatherOrder.Pressure -> weathers.values.sortedByDescending { day ->
                        day.map { weather -> weather.pressure }.average()
                    }
                    is WeatherOrder.Humidity -> weathers.values.sortedByDescending { day ->
                        day.map { weather -> weather.humidity }.average()
                    }
                }
            }
        }.mapIndexed{index, weatherData -> index to weatherData }.toMap()
    }
}