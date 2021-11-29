package com.ivan.data.converter

import com.ivan.data.dto.WeatherDto
import com.ivan.domain.model.*
import javax.inject.Inject

class WeatherConverter @Inject constructor() {
    fun toEntity(weatherDto: WeatherDto) : Weather {
        val weather = weatherDto.weather.first()

        return Weather(
            description = weather.description,
            humidity = weatherDto.main?.humidity,
            pressure = weatherDto.main?.pressure,
            temperature = weatherDto.main?.temp,
            minTemperature = weatherDto.main?.tempMin,
            maxTemperature = weatherDto.main?.tempMax,
            wind = Wind(weatherDto.wind?.speed, weatherDto.wind?.deg),
            type = mapToWeatherType(weather.id)
        )
    }

    private fun mapToWeatherType(weatherId: Int?): WeatherType {
        // https://openweathermap.org/weather-conditions
        return when (weatherId) {
            in 200..232 -> WeatherType.THUNDERSTORM
            in 300..321 -> WeatherType.DRIZZLE
            in 500..504 -> WeatherType.RAIN
            511 -> WeatherType.SNOW
            in 520..531 -> WeatherType.DRIZZLE
            in 600..622 -> WeatherType.SNOW
            in 701..781 -> WeatherType.FOG
            800 -> WeatherType.CLEAR
            801 -> WeatherType.LIGHT_CLOUDS
            802 -> WeatherType.CLOUDS
            in 803..804 -> WeatherType.OVERCAST_CLOUDS
            else -> WeatherType.UNDEFINED
        }
    }
}