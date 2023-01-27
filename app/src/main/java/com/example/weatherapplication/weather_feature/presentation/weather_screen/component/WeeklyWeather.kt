package com.example.weatherapplication.weather_feature.presentation.weather_screen.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherapplication.R
import com.example.weatherapplication.weather_feature.domain.utils.OrderType
import com.example.weatherapplication.weather_feature.domain.utils.WeatherOrder
import com.example.weatherapplication.weather_feature.presentation.weather_screen.WeatherState
import java.util.*
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyWeather(
    modifier: Modifier = Modifier,
    state: WeatherState,
    onFiltering: (weatherOrder: WeatherOrder) -> Unit,
    onClick: (index: Int) -> Unit
){
    val config = LocalConfiguration.current

   Column(
       modifier = modifier
           .fillMaxWidth(),
   ) {
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(top = 8.dp),
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Box(
               modifier = Modifier.width((config.screenWidthDp/4).dp),
               contentAlignment = Alignment.Center
           ){
               Text(
                   text = "Day",
                   color = Color.White,
                   )
           }

           WeekWeatherFilterOption(
               title = "Pressure",
               modifier = Modifier.width((config.screenWidthDp/4).dp),
               weatherOrder = WeatherOrder.Pressure(OrderType.Ascending),
               unit = "hpa",
               icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
               iconTint = Color.White,
               textStyle = TextStyle(
                   color = Color.White
               )
           ){orderType ->
               onFiltering(WeatherOrder.Pressure(orderType?: OrderType.Ascending))
           }
           WeekWeatherFilterOption(
               title = "Humidity",
               modifier = Modifier.width((config.screenWidthDp/4).dp),
               weatherOrder = WeatherOrder.Humidity(OrderType.Ascending),
               unit = "%",
               icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
               iconTint = Color.White,
               textStyle = TextStyle(
                   color = Color.White
               )
           ){orderType ->
               onFiltering(WeatherOrder.Humidity(orderType?: OrderType.Ascending))
           }
           WeekWeatherFilterOption(
               title = "Temperature",
               modifier = Modifier.width((config.screenWidthDp/4).dp),
               weatherOrder = WeatherOrder.TemperatureCelsius(OrderType.Ascending),
               unit = "°C",
               icon = ImageVector.vectorResource(id = R.drawable.ic_sunny),
               iconTint = Color.White,
               textStyle = TextStyle(
                   color = Color.White
               )
           ){orderType ->
               onFiltering(WeatherOrder.TemperatureCelsius(orderType?: OrderType.Ascending))
           }
       }
       Spacer(modifier = Modifier.height(16.dp))

       Column(
           modifier = Modifier.fillMaxWidth(),
           verticalArrangement = Arrangement.spacedBy(8.dp)
       ) {
           state.weatherInfo?.weatherDataPearDay?.let {
               it.onEachIndexed { index,  weather ->
                   Row(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(vertical = 10.dp)
                           .clickable {
                              onClick(index)
                           },
                       horizontalArrangement = Arrangement.SpaceBetween
                   ) {
                       propertyValue("${weather.value.first().time.dayOfWeek.name
                           .substring(0, 3).uppercase()}."
                       )

                       propertyValue("${weather.value.map { it.pressure }.average().roundToInt()}")

                       propertyValue("${weather.value.map { it.humidity }.average().roundToInt()}")

                       propertyValue("${weather.value.map { it.temperatureCelsius }.average().roundToInt()}")
                   }

               }
           }
       }
   }
}
@Composable
fun propertyValue(
    value: String,
    textColor: Color = Color.White,
){
    val config = LocalConfiguration.current
    Box(
        modifier = Modifier.width((config.screenWidthDp/4).dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = value,
            textAlign = TextAlign.Start,
            color = textColor
        )
    }
}