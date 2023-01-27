package com.example.weatherapplication.weather_feature.presentation.weather_screen.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapplication.weather_feature.domain.weather.WeatherData
import com.example.weatherapplication.weather_feature.presentation.weather_screen.WeatherState
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier,
    onHourClick: (weather: WeatherData) -> Unit
){
    val now = LocalDateTime.now()
    state.weatherInfo?.currentWeatherData?.let{data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            val weatherForDay = state.weatherInfo.weatherDataPearDay.values.find {
                it.first().time.dayOfYear == data.time.dayOfYear
            }
            var selectedIndex by remember{
                mutableStateOf(
                    weatherForDay?.indexOfFirst {
                        val hour = if(now.minute < 30) now.hour else now.hour +1
                        it.time.hour == hour
                    },
                )
            }

            Text(
                text = if(now.dayOfYear == state.weatherInfo.currentWeatherData.time.dayOfYear) "Today"
                else "${state.weatherInfo.currentWeatherData.time.dayOfWeek?.name}",
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            ScrollableTabRow(
                selectedTabIndex = selectedIndex?:0,
                indicator = {},
                modifier = Modifier.fillMaxWidth()
            ){
                weatherForDay?.forEachIndexed{index, weather ->
                    Tab(
                        selected = state.weatherInfo.currentWeatherData.time == weather.time,
                        onClick = {
                            onHourClick(weather)
                            selectedIndex = index
                        },
                        modifier = Modifier
                            .background(
                            shape = RoundedCornerShape(10.dp),
                            color = if(state.weatherInfo.currentWeatherData.time == weather.time)
                                MaterialTheme.colors.primaryVariant
                            else MaterialTheme.colors.primary
                        ),
                    ) {
                        HourlyWeatherDisplay(
                            weatherData = weather,
                            modifier = Modifier
                                .height(100.dp),
                            textColor = Color.LightGray
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Weekly weather",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
