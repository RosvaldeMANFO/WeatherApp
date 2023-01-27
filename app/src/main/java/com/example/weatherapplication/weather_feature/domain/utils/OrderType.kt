package com.example.weatherapplication.weather_feature.domain.utils

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}