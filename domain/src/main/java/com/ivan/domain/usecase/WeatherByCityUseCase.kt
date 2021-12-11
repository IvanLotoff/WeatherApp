package com.ivan.domain.usecase

import com.ivan.domain.model.Weather
import com.ivan.domain.repository.WeatherRepository

class WeatherByCityUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun get(city: String): Weather {
        return weatherRepository.getWeatherByCity(city)
    }
}