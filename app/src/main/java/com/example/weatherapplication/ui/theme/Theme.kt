package com.example.weatherapplication.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Spring
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime

enum class Season{
    WINTER,
    SPRING,
    SUMMER,
    FALL
}


private val WinterThemeColor = lightColors(
    primary = WinterPrimary,
    primaryVariant = WinterPrimaryVariant,
    onPrimary = Color.White,
    secondary = WinterSecondary,
    onSecondary = Color.White,
    error = RedWhite
)
private val SpringThemeColor= lightColors(
    primary = SpringPrimary,
    primaryVariant = SpringPrimaryVariant,
    onPrimary = Color.White,
    secondary = SpringSecondary,
    onSecondary = Color.White,
    error = RedWhite
)
private val SummerThemeColor = lightColors(
    primary = SummerPrimary,
    primaryVariant = SummerPrimaryVariant,
    onPrimary = Color.White,
    secondary = SummerSecondary,
    onSecondary = Color.White,
    error = RedWhite
)
private val FallThemeColor = lightColors(
    primary = FallPrimary,
    primaryVariant = FallPrimaryVariant,
    onPrimary = Color.White,
    secondary = FallSecondary,
    onSecondary = Color.White,
    error = RedWhite
)

@Composable
fun WeatherApplicationTheme(
    season: Season,
    content: @Composable () -> Unit
) {
    when(season){
        Season.WINTER ->{
            MaterialTheme(
                colors = WinterThemeColor,
                typography = Typography,
                shapes = Shapes,
                content = content
            )
        }
        Season.SPRING ->{
            MaterialTheme(
                colors = SpringThemeColor,
                typography = Typography,
                shapes = Shapes,
                content = content
            )
        }
        Season.SUMMER ->{
            MaterialTheme(
                colors = SummerThemeColor,
                typography = Typography,
                shapes = Shapes,
                content = content
            )
        }
        Season.FALL ->{
            MaterialTheme(
                colors = FallThemeColor,
                typography = Typography,
                shapes = Shapes,
                content = content
            )
        }
    }
}