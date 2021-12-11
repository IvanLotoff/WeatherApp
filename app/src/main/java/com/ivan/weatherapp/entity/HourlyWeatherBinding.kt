package com.ivan.weatherapp.entity

import com.ivan.domain.model.WeatherType

class HourlyWeatherBinding(
    val resourceId: Int?,
    val description: String?,
    val temp: String,
    val main: String
)