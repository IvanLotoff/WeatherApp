package com.ivan.domain.usecase

import com.ivan.domain.model.HourlyWeather
import com.ivan.domain.repository.WeatherRepository

class WeatherForecastByHoursUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun get(lat: Double, lon: Double): List<HourlyWeather> {
        return weatherRepository.getWeatherForecastByHours(latitude = lat, longitude = lon)
    }
}