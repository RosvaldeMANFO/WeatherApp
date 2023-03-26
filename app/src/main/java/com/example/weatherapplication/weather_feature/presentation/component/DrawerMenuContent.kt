package com.example.weatherapplication.weather_feature.presentation.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.ShieldMoon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapplication.ui.theme.Season
import com.example.weatherapplication.weather_feature.domain.weather.DrawerOptionType
import com.example.weatherapplication.weather_feature.presentation.WeatherViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DrawerMenuContent(
    viewModel: WeatherViewModel = hiltViewModel(),
    season: Season,
    contentColor: Color,
    backgroundColor: Color,
    onClick: (option: DrawerOptionType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(backgroundColor)
            .fillMaxHeight()
    ) {
        DrawerHeader(
            imageId = season.imageRes,
            season = season,
            color = contentColor
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            DrawerItem(
                title = if(viewModel.lightMode) "Dark mode" else "Light mode",
                contentColor = contentColor
            ) {
                ToggleButton(
                    isChecked = viewModel.lightMode,
                    contentColor = contentColor,
                    icon = if(viewModel.lightMode) Icons.Filled.Light else Icons.Filled.ShieldMoon,
                    onClick = {onClick(DrawerOptionType.ToggleMode)}
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DrawerHeader(
    imageId: Int,
    season: Season,
    color: Color,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(id = imageId) ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = season.weatherTitle,
            fontSize = 30.sp,
            textAlign = TextAlign.Start,
            color = color
        )
    }
}

@Composable
fun DrawerItem(
    title: String,
    contentColor: Color,
    content: @Composable () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            color = contentColor,
        )

        content()
    }
}