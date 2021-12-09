package com.ivan.domain.usecase

import com.ivan.domain.model.Weather
import com.ivan.domain.repository.WeatherRepository

class WeatherByLocationUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lon: Double): Weather {
        return weatherRepository.getWeatherByLocation(lat = lat, lon = lon)
    }
}