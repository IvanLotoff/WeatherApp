package com.ivan.domain.repository

import com.ivan.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherByCity(placeName: String): Weather
    suspend fun getWeatherByLocation(lat: Double, lon: Double): Weather
}