package com.ivan.domain.usecase

import com.ivan.domain.model.Weather
import com.ivan.domain.repository.WeatherRepository

class WeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(city: String): Weather {
        return weatherRepository.getWeatherByCity(city)
    }
}