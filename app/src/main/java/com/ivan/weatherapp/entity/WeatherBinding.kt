package com.ivan.weatherapp.entity

import androidx.annotation.IdRes
import com.ivan.domain.model.WeatherType
import com.ivan.domain.model.Wind

data class WeatherBinding(
    val description: String?,
    val resourceId: Int?,
    val temperature: Double?,
    val maxTemperature: Double?,
    val minTemperature: Double?,
    val pressure: Int?,
    val humidity: Int?,
    val wind: Wind
)