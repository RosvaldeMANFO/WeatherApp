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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapplication.weather_feature.domain.use_case.GetSeasonUseCase
import com.example.weatherapplication.weather_feature.presentation.weather_screen.WeatherViewModel
import com.example.weatherapplication.weather_feature.presentation.weather_screen.component.WeatherCard
import com.example.weatherapplication.weather_feature.presentation.weather_screen.component.WeatherForecast
import com.example.weatherapplication.ui.theme.Season
import com.example.weatherapplication.ui.theme.WeatherApplicationTheme
import com.example.weatherapplication.weather_feature.presentation.util.Screen
import com.example.weatherapplication.weather_feature.presentation.weather_screen.WeatherScreen
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
            val navController = rememberNavController()
            WeatherApplicationTheme(
                season!!
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.WeatherScreen.route
                ){
                    composable(route = Screen.WeatherScreen.route){
                       WeatherScreen(viewModel)
                    }
                }
            }
        }
    }

    private fun setStatusBarColor(season: Season?){
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        when(season){
            Season.WINTER -> window.statusBarColor = ContextCompat.getColor(this, R.color.WinterPrimary)
            Season.SPRING -> window.statusBarColor = ContextCompat.getColor(this, R.color.SpringPrimary)
            Season.SUMMER -> window.statusBarColor = ContextCompat.getColor(this, R.color.SummerPrimary)
            Season.FALL -> window.statusBarColor = ContextCompat.getColor(this, R.color.FallPrimary)
        }
    }
}