package com.ivan.weatherapp.converter

import com.ivan.domain.model.Weather
import com.ivan.domain.model.WeatherType
import com.ivan.weatherapp.R
import com.ivan.weatherapp.entity.WeatherBinding
import javax.inject.Inject

class WeatherConverter @Inject constructor() {
    fun entityToBindingEntity(entity: Weather): WeatherBinding {
        return WeatherBinding(
            description = entity.description,
            resourceId = mapToDrawable(entity.type),
            temperature = entity.temperature,
            maxTemperature = entity.maxTemperature,
            minTemperature = entity.minTemperature,
            pressure = entity.pressure,
            humidity = entity.humidity,
            wind = entity.wind
        )
    }
}

fun mapToDrawable(type: WeatherType) : Int? {
    if(type == WeatherType.CLEAR)
        return R.drawable.clear
    if(type == WeatherType.RAIN)
        return R.drawable.rain
    if(type == WeatherType.CLOUDS)
        return R.drawable.clouds
    if(type == WeatherType.DRIZZLE)
        return R.drawable.drizzle
    if(type == WeatherType.FOG)
        return R.drawable.fog
    if(type == WeatherType.LIGHT_CLOUDS)
        return R.drawable.lightclouds
    if(type == WeatherType.OVERCAST_CLOUDS)
        return R.drawable.overcastclouds
    if(type == WeatherType.THUNDERSTORM)
        return R.drawable.storm
    return null;
}