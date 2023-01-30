package com.example.weatherapplication.weather_feature.presentation

import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.weatherapplication.ui.theme.Season
import com.example.weatherapplication.ui.theme.lightMode
import com.example.weatherapplication.weather_feature.presentation.component.*
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

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {},
            )
        },
    ) { it ->
        scaffoldContentPadding = it
        Box(
            modifier = Modifier
                .fillMaxSize()
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
                            contentColor = MaterialTheme.colors.onPrimary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        WeatherForecast(
                            state = viewModel.state,
                            contentColor = MaterialTheme.colors.onPrimary
                        ) {
                            viewModel.onEvent(WeatherEvents.ChangeHourlyWeather(it))
                        }
                        WeeklyWeather(
                            modifier = Modifier
                                .background(
                                    shape = RoundedCornerShape(
                                        topStart = 10.dp,
                                        topEnd = 10.dp
                                    ),
                                    color = MaterialTheme.colors.secondary
                                )
                                .align(Alignment.BottomCenter),
                            state = viewModel.state,
                            onFiltering = { weatherOrder ->
                                viewModel.onEvent(WeatherEvents.FilterWeekWeather(weatherOrder))
                            },
                            onClick = { index ->
                                viewModel.onEvent(WeatherEvents.ChangeDay(index))
                            },
                            contentColor = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
            if (viewModel.state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colors.onPrimary
                )
            }
            viewModel.state.error?.let {
                ErrorCard(
                    icon = Icons.Outlined.CloudOff,
                    errorColor = MaterialTheme.colors.error
                )
            }
        }
    }
}


