package com.example.weatherapplication.weather_feature.domain.utils

sealed class WeatherOrder(val orderType: OrderType) {
    class TemperatureCelsius(orderType: OrderType): WeatherOrder(orderType)
    class Pressure(orderType: OrderType): WeatherOrder(orderType)
    class Humidity(orderType: OrderType): WeatherOrder(orderType)
}