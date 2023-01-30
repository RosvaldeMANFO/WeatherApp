package com.example.weatherapplication.weather_feature.domain.weather

sealed class DrawerOptionType {
    object About: DrawerOptionType()
    object ToggleMode: DrawerOptionType()
}