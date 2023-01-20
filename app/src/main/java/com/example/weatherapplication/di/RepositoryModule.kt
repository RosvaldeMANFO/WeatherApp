package com.example.weatherapplication.di

import com.example.weatherapplication.data.location.DefaultLocationTracker
import com.example.weatherapplication.data.repository.WeatherRepositoryImpl
import com.example.weatherapplication.domain.location.LocationTracker
import com.example.weatherapplication.domain.repository.WeatherRepository
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