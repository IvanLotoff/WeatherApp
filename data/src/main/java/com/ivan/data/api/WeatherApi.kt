package com.ivan.data.api

import com.ivan.data.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherByCityName(@Query("q") cityName: String, @Query("appid") apiKey: String, @Query("units") units:String = "metric"): WeatherDto
}