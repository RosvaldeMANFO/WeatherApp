package com.example.weatherapplication.weather_feature.domain.use_case

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapplication.ui.theme.Season
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class GetSeasonUseCase {
    private val springMonths = listOf(3, 4, 5)
    private val summerMonths = listOf(6, 7, 8)
    private val fallMonths = listOf(9, 10, 11)
    private val winterMonths = listOf(12, 1, 2)

    operator fun invoke(date: LocalDateTime):Season?{
        val month = date.monthValue
        if(springMonths.contains(month))
            return Season.Spring
        if(summerMonths.contains(month))
            return Season.Summer
        if(fallMonths.contains(month))
            return Season.Fall
        if(winterMonths.contains(month))
            return Season.Winter
        return null
    }
}