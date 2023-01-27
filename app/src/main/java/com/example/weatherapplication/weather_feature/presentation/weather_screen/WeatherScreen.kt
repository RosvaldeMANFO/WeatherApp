package com.example.weatherapplication.weather_feature.presentation.weather_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapplication.ui.theme.Season
import com.example.weatherapplication.weather_feature.presentation.util.ErrorCard
import com.example.weatherapplication.weather_feature.presentation.weather_screen.component.DrawerMenuContent
import com.example.weatherapplication.weather_feature.presentation.weather_screen.component.WeatherCard
import com.example.weatherapplication.weather_feature.presentation.weather_screen.component.WeatherForecast
import com.example.weatherapplication.weather_feature.presentation.weather_screen.component.WeeklyWeather
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    season: Season
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()
    var scaffoldContentPadding by remember {
        mutableStateOf(PaddingValues(16.dp))
    }

    var isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 10.dp
    val modifier = if (isSheetFullScreen)
        Modifier.fillMaxSize()
    else
        Modifier.fillMaxWidth()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                actions = { },
            )
        },
        drawerBackgroundColor = MaterialTheme.colors.primary,
        drawerContent = {
            DrawerMenuContent(
                onClick = {},
                season = season
            )
        },
        drawerGesturesEnabled = true
    ) {
        scaffoldContentPadding = it
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        ) {
            if(!viewModel.state.isLoading && viewModel.state.error == null){
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .fillMaxHeight()
                ) {
                    item {
                        WeatherCard(
                            viewModel.state.weatherInfo?.currentWeatherData,
                            backgroundColor = MaterialTheme.colors.primaryVariant,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        WeatherForecast(state = viewModel.state) {
                            viewModel.onEvent(WeatherEvents.ChangeHourlyWeather(it))
                        }
                        WeeklyWeather(
                            modifier = modifier
                                .background(
                                    shape = RoundedCornerShape(
                                        topStart = roundedCornerRadius,
                                        topEnd = roundedCornerRadius
                                    ),
                                    color = MaterialTheme.colors.secondary
                                )
                                .align(Alignment.BottomCenter),
                            state = viewModel.state,
                            onFiltering = {
                                viewModel.onEvent(WeatherEvents.FilterWeekWeather(it))
                            },
                            onClick = {
                                viewModel.onEvent(WeatherEvents.ChangeDay(it))
                            }
                        )
                    }
                }
            }
            if (viewModel.state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
            viewModel.state.error?.let { error ->
                ErrorCard(
                    icon = Icons.Outlined.CloudOff,
                    errorColor = MaterialTheme.colors.error
                )
            }
        }
    }
}


