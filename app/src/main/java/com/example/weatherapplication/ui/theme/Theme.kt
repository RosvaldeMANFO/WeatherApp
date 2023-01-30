package com.example.weatherapplication.ui.theme

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.weatherapplication.R
import java.time.LocalDateTime

var lightMode = true

sealed class Season(
    val weatherTitle: String,
    @DrawableRes val imageRes: Int
){
    object Winter: Season(
        weatherTitle = "Winter",
        imageRes = R.mipmap.winter
    )
    object Summer: Season(
        weatherTitle = "Summer",
        imageRes = R.mipmap.summer
    )
    object Fall: Season(
        weatherTitle = "Fall",
        imageRes = R.mipmap.fall
    )
    object Spring: Season(
        weatherTitle = "Spring",
        imageRes = R.mipmap.spring
    )

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

private val DarkMode = darkColors(
    primary = DarkPrimary,
    primaryVariant = DarkPrimaryVariant,
    onPrimary = DarkOnPrimary,
    secondary = DarkSecondary,
    onSecondary = DarkOnPrimary,
    error = RedDark
)

@Composable
fun WeatherApplicationTheme(
    dark: Boolean = isSystemInDarkTheme(),
    season: Season,
    content: @Composable () -> Unit
) {

   if(!dark){
       when(season){
           Season.Winter ->{
               MaterialTheme(
                   colors = WinterThemeColor,
                   typography = Typography,
                   shapes = Shapes,
                   content = content
               )
           }
           Season.Spring ->{
               MaterialTheme(
                   colors = SpringThemeColor,
                   typography = Typography,
                   shapes = Shapes,
                   content = content
               )
           }
           Season.Summer ->{
               MaterialTheme(
                   colors = SummerThemeColor,
                   typography = Typography,
                   shapes = Shapes,
                   content = content
               )
           }
           Season.Fall ->{
               MaterialTheme(
                   colors = FallThemeColor,
                   typography = Typography,
                   shapes = Shapes,
                   content = content
               )
           }
       }
   }else{
       MaterialTheme(
           colors = DarkMode,
           typography = Typography,
           shapes = Shapes,
           content = content
       )
   }
}