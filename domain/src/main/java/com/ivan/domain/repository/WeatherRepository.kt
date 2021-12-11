package com.ivan.domain.repository

import com.ivan.domain.model.HourlyWeather
import com.ivan.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherByCity(placeName: String): Weather
    suspend fun getWeatherByLocation(lat: Double, lon: Double): Weather
    suspend fun getWeatherForecastByHours(latitude: Double, longitude: Double, timeType : String = "daily"): List<HourlyWeather>
}