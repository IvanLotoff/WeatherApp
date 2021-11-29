package com.ivan.data.repository

import com.ivan.data.BuildConfig
import com.ivan.data.api.WeatherApi
import com.ivan.data.converter.WeatherConverter
import com.ivan.domain.model.Weather
import com.ivan.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherConverter: WeatherConverter
): WeatherRepository {
    override suspend fun getWeatherByCity(placeName: String): Weather {
        val dto = weatherApi.getWeatherByCityName(placeName, BuildConfig.APIKEY)
        return weatherConverter.toEntity(dto)
    }
}
