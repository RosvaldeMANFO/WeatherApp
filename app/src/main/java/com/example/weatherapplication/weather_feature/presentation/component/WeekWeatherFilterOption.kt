package com.example.weatherapplication.weather_feature.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.weatherapplication.weather_feature.domain.utils.OrderType
import com.example.weatherapplication.weather_feature.domain.utils.WeatherOrder

@Composable
fun WeekWeatherFilterOption(
    title: String,
    weatherOrder: WeatherOrder,
    icon: ImageVector,
    unit: String,
    iconTint: Color,
    textStyle: TextStyle = TextStyle(),
    modifier: Modifier = Modifier,
    onClick: (orderType: OrderType?) -> Unit
){
    var orderType by remember {
        mutableStateOf<OrderType?>(weatherOrder.orderType)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable {
               orderType = if(orderType == OrderType.Ascending){
                   OrderType.Descending
               }else{
                   OrderType.Ascending
               }
                onClick(orderType)
            },
    ){
        if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT){
            Icon(
                imageVector = icon,
                contentDescription =null,
                tint = iconTint,
                modifier = Modifier.size(25.dp)
            )
        }
        else{
            Text(
                text = title,
                style = textStyle
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = unit,
            style = textStyle
        )
        Icon(
            imageVector = if(orderType == OrderType.Ascending) Icons.Filled.KeyboardArrowUp
            else Icons.Filled.KeyboardArrowDown,
            contentDescription =null,
            tint = iconTint,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
    }
}