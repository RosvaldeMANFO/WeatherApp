package com.example.weatherapplication.weather_feature.presentation.weather_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.HeadsetOff
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.icons.sharp.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapplication.weather_feature.presentation.util.ErrorCard
import com.example.weatherapplication.weather_feature.presentation.weather_screen.component.WeatherCard
import com.example.weatherapplication.weather_feature.presentation.weather_screen.component.WeatherForecast

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel
){

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
        ) {
            WeatherCard(
                state = viewModel.state,
                backgroundColor = MaterialTheme.colors.primaryVariant,
            )
            Spacer(modifier = Modifier.height(16.dp))
            WeatherForecast(state = viewModel.state)
        }
        if(viewModel.state.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        viewModel.state.error?.let{error ->
           ErrorCard(
               icon = Icons.Outlined.CloudOff,
               errorColor = MaterialTheme.colors.error
           )
        }
    }
}