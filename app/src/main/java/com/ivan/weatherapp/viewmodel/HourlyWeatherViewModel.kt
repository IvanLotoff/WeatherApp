package com.ivan.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivan.domain.usecase.WeatherForecastByHoursUseCase
import com.ivan.weatherapp.converter.HourlyWeatherConverter
import com.ivan.weatherapp.entity.HourlyWeatherBinding
import com.ivan.weatherapp.entity.WeatherBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HourlyWeatherViewModel @Inject constructor(
    private val weatherForecastByHoursUseCase: WeatherForecastByHoursUseCase,
    private val hourlyWeatherConverter: HourlyWeatherConverter
): ViewModel() {
    private val _uiStateFlow = MutableStateFlow<HourlyWeatherUIState>(HourlyWeatherUIState.NewState)
    val uiStateFlow: StateFlow<HourlyWeatherUIState> = _uiStateFlow.asStateFlow()

    fun fetchHourlyWeatherByLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiStateFlow.value = HourlyWeatherUIState.Loading
            try {
                val resp = weatherForecastByHoursUseCase.get(lat = lat, lon = lon)
                val hourlyWeatherBinding = hourlyWeatherConverter.entityToBindingEntity(resp)
                _uiStateFlow.value = HourlyWeatherUIState.Success(hourlyWeatherBinding)
            } catch (ex: Throwable){
                _uiStateFlow.value = HourlyWeatherUIState.Failure(ex)
            }
        }
    }
}

sealed class HourlyWeatherUIState {
    object NewState: HourlyWeatherUIState()
    object Loading: HourlyWeatherUIState()
    class Failure(val ex: Throwable?): HourlyWeatherUIState()
    class Success(val weather: List<HourlyWeatherBinding>): HourlyWeatherUIState()
}