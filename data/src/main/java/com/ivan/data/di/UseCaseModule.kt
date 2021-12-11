package com.ivan.data.di

import com.ivan.data.api.WeatherApi
import com.ivan.data.converter.HourlyWeatherConverter
import com.ivan.data.converter.WeatherConverter
import com.ivan.data.repository.WeatherRepositoryImpl
import com.ivan.domain.usecase.WeatherByCityUseCase
import com.ivan.domain.usecase.WeatherByLocationUseCase
import com.ivan.domain.usecase.WeatherForecastByHoursUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class UseCaseModule {
    @Provides
    fun weatherUseCase(
        api: WeatherApi,
        converter: WeatherConverter,
        hourlyWeatherConverter: HourlyWeatherConverter
    ): WeatherByCityUseCase {
        return WeatherByCityUseCase(WeatherRepositoryImpl(api, converter, hourlyWeatherConverter))
    }

    @Provides
    fun weatherByLocationUseCase(
        api: WeatherApi,
        converter: WeatherConverter,
        hourlyWeatherConverter: HourlyWeatherConverter
    ): WeatherByLocationUseCase {
        return WeatherByLocationUseCase(
            WeatherRepositoryImpl(
                api,
                converter,
                hourlyWeatherConverter
            )
        )
    }

    @Provides
    fun weatherForecastByLocationUseCase(
        api: WeatherApi,
        converter: WeatherConverter,
        hourlyWeatherConverter: HourlyWeatherConverter
    ): WeatherForecastByHoursUseCase {
        return WeatherForecastByHoursUseCase(
            WeatherRepositoryImpl(
                api,
                converter,
                hourlyWeatherConverter
            )
        )
    }
}