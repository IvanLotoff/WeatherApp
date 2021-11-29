package com.ivan.data.remote

import com.ivan.data.api.WeatherApi
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private val weatherApi: WeatherApi) {

}