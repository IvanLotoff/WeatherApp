package com.ivan.data.repository

import com.ivan.data.BuildConfig
import com.ivan.data.api.WeatherApi
import com.ivan.data.converter.HourlyWeatherConverter
import com.ivan.data.converter.WeatherConverter
import com.ivan.domain.model.HourlyWeather
import com.ivan.domain.model.Weather
import com.ivan.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherConverter: WeatherConverter,
    private val hourlyWeatherConverter: HourlyWeatherConverter
): WeatherRepository {

    override suspend fun getWeatherByCity(placeName: String): Weather {
        val dto = weatherApi.getWeatherByCityName(placeName, BuildConfig.APIKEY)
        return weatherConverter.toEntity(dto)
    }

    override suspend fun getWeatherByLocation(lat: Double, lon: Double): Weather {
        val dto = weatherApi.getCurrentWeatherByGeoposition(
            latitude = lat,
            longitude = lon,
            BuildConfig.APIKEY
        )
        return weatherConverter.toEntity(dto)
    }

    override suspend fun getWeatherForecastByHours(
        latitude: Double,
        longitude: Double,
        timeType: String
    ): List<HourlyWeather> {
        val dto = weatherApi.getHourlyWeather(
            latitude = latitude,
            longitude = longitude,
            timeType = timeType,
            apikey = BuildConfig.APIKEY
        )

        return hourlyWeatherConverter.toEntity(dto)
    }
}
