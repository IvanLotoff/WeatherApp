package com.ivan.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivan.domain.model.Weather
import com.ivan.domain.usecase.WeatherUseCase
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
    private val weatherUseCase: WeatherUseCase,
    private val weatherConverter: WeatherConverter
): ViewModel() {

    private val _uiStateFlow = MutableStateFlow<WeatherUIState>(WeatherUIState.NewState)
    val uiStateFlow: StateFlow<WeatherUIState> = _uiStateFlow.asStateFlow()

    fun fetchWeatherByCity(city: String) {
        viewModelScope.launch {
            _uiStateFlow.value = WeatherUIState.Loading
            try {
                val resp = withContext(Dispatchers.IO) {
                    weatherUseCase(city)
                }
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