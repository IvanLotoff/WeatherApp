package com.ivan.weatherapp.converter

import com.ivan.domain.model.HourlyWeather
import com.ivan.weatherapp.entity.HourlyWeatherBinding
import javax.inject.Inject

class HourlyWeatherConverter @Inject constructor() {

    fun entityToBindingEntity(entityList: List<HourlyWeather>): List<HourlyWeatherBinding> {
        return entityList.map {
            HourlyWeatherBinding(
                description = it.description,
                resourceId = mapToDrawable(it.type),
                temp = it.temp.toString(),
                main = it.main
            )
        }
    }
}