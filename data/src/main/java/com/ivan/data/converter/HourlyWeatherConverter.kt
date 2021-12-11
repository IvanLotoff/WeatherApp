package com.ivan.data.converter

import com.ivan.data.dto.WeatherByHoursDto
import com.ivan.domain.model.HourlyWeather
import javax.inject.Inject

class HourlyWeatherConverter @Inject constructor() {
    fun toEntity(weatherByHoursDto: WeatherByHoursDto): List<HourlyWeather> {
        return weatherByHoursDto.hourly?.map {
            HourlyWeather(
                type = mapToWeatherType(it.weather?.get(0)?.id!!),
                main = it.weather?.get(0)?.main!!,
                description = it.weather?.get(0)?.description,
                temp = it.temp
            )
        }!!
    }
}