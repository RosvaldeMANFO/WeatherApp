package com.example.weatherapplication.di

import com.example.weatherapplication.weather_feature.data.location.DefaultLocationTracker
import com.example.weatherapplication.weather_feature.data.repository.WeatherRepositoryImpl
import com.example.weatherapplication.weather_feature.domain.location.LocationTracker
import com.example.weatherapplication.weather_feature.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}