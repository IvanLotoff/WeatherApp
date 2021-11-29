package com.ivan.domain.model

data class Weather(
    val description: String?,
    val type: WeatherType,
    val temperature: Double?,
    val maxTemperature: Double?,
    val minTemperature: Double?,
    val pressure: Int?,
    val humidity: Int?,
    val wind: Wind
)