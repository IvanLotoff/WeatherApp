package com.ivan.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivan.domain.usecase.WeatherByCityUseCase
import com.ivan.domain.usecase.WeatherByLocationUseCase
import com.ivan.domain.usecase.WeatherForecastByHoursUseCase
import com.ivan.weatherapp.converter.WeatherConverter
import com.ivan.weatherapp.entity.WeatherBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherByCityUseCase: WeatherByCityUseCase,
    private val weatherByLocationUseCase: WeatherByLocationUseCase,
    private val weatherConverter: WeatherConverter
): ViewModel() {

    private val _uiStateFlow = MutableStateFlow<WeatherUIState>(WeatherUIState.NewState)
    val uiStateFlow: StateFlow<WeatherUIState> = _uiStateFlow.asStateFlow()

    fun fetchWeatherByCity(city: String) {
        viewModelScope.launch {
            _uiStateFlow.value = WeatherUIState.Loading
            try {
                val resp = weatherByCityUseCase.get(city)
                val weatherBinding = weatherConverter.entityToBindingEntity(resp)
                _uiStateFlow.value = WeatherUIState.Success(weatherBinding)
            } catch (ex: Throwable){
                _uiStateFlow.value = WeatherUIState.Failure(ex)
            }
        }
    }

    fun fetchWeatherByLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiStateFlow.value = WeatherUIState.Loading
            try {
                val resp = weatherByLocationUseCase.get(lat = lat, lon = lon)
                val weatherBinding = weatherConverter.entityToBindingEntity(resp)
                _uiStateFlow.value = WeatherUIState.Success(weatherBinding)
            } catch (ex: Throwable){
                _uiStateFlow.value = WeatherUIState.Failure(ex)
            }
        }
    }
}

sealed class WeatherUIState {
    object NewState: WeatherUIState()
    object Loading: WeatherUIState()
    class Failure(val ex: Throwable?): WeatherUIState()
    class Success(val weather: WeatherBinding): WeatherUIState()
}