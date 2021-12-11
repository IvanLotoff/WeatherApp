package com.ivan.domain.model

class HourlyWeather(
    val type: WeatherType,
    val description: String?,
    val temp: Double?,
    val main: String
)