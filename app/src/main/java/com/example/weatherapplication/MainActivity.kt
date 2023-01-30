package com.example.weatherapplication

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import com.example.weatherapplication.weather_feature.domain.use_case.GetSeasonUseCase
import com.example.weatherapplication.weather_feature.presentation.WeatherViewModel
import com.example.weatherapplication.ui.theme.Season
import com.example.weatherapplication.ui.theme.WeatherApplicationTheme
import com.example.weatherapplication.ui.theme.lightMode
import com.example.weatherapplication.weather_feature.presentation.WeatherScreen
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        ))

        setContent {
            val season by remember{
                mutableStateOf(
                    GetSeasonUseCase().invoke(
                        viewModel.state.weatherInfo?.currentWeatherData?.time ?:
                        LocalDateTime.now()
                    )
                )
            }
            setStatusBarColor(season)
            season?.let { season ->
                WeatherApplicationTheme(
                    isSystemInDarkTheme(),
                    season
                ) {
                    WeatherScreen(viewModel, season)
                }
            }
        }
    }

    private fun setStatusBarColor(
        season: Season?
    ){

        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if(lightMode){
            when(season){
                Season.Winter -> window.statusBarColor = ContextCompat.getColor(this, R.color.WinterPrimary)
                Season.Spring -> window.statusBarColor = ContextCompat.getColor(this, R.color.SpringPrimary)
                Season.Summer -> window.statusBarColor = ContextCompat.getColor(this, R.color.SummerPrimary)
                Season.Fall -> window.statusBarColor = ContextCompat.getColor(this, R.color.FallPrimary)
            }
        }else{
            window.statusBarColor = ContextCompat.getColor(this, R.color.DarkPrimary)
        }
    }
}