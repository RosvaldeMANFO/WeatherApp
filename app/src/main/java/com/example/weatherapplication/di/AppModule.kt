package com.example.weatherapplication.di

import android.app.Application
import android.text.TextUtils
import com.example.weatherapplication.di.Constants.CONNECT_TIMEOUT
import com.example.weatherapplication.di.Constants.READ_TIMEOUT
import com.example.weatherapplication.di.Constants.TIME_OUT
import com.example.weatherapplication.di.Constants.WRITE_TIMEOUT
import com.example.weatherapplication.weather_feature.data.data_source.WeatherApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {

        val timeoutInterceptor = Interceptor { chain ->
            val request: Request = chain.request()
            var connectTimeout = chain.connectTimeoutMillis()
            var readTimeout = chain.readTimeoutMillis()
            var writeTimeout = chain.writeTimeoutMillis()
            val connectNew: String? = request.header(CONNECT_TIMEOUT)
            val readNew: String? = request.header(READ_TIMEOUT)
            val writeNew: String? = request.header(WRITE_TIMEOUT)
            if (!TextUtils.isEmpty(connectNew)) {
                connectTimeout = Integer.valueOf(connectNew)
            }
            if (!TextUtils.isEmpty(readNew)) {
                readTimeout = Integer.valueOf(readNew)
            }
            if (!TextUtils.isEmpty(writeNew)) {
                writeTimeout = Integer.valueOf(writeNew)
            }
            chain
                .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .proceed(request)
        }

        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .addInterceptor(timeoutInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient{
        return LocationServices.getFusedLocationProviderClient(app)
    }
}