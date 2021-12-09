package com.ivan.data.di

import com.ivan.data.api.WeatherApi
import com.ivan.data.converter.WeatherConverter
import com.ivan.data.repository.WeatherRepositoryImpl
import com.ivan.domain.usecase.WeatherByCityUseCase
import com.ivan.domain.usecase.WeatherByLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class UseCaseModule {
    @Provides
    fun weatherUseCase(api: WeatherApi, converter: WeatherConverter): WeatherByCityUseCase {
        return WeatherByCityUseCase(WeatherRepositoryImpl(api, converter))
    }

    @Provides
    fun weatherByLocationUseCase(api: WeatherApi, converter: WeatherConverter): WeatherByLocationUseCase {
        return WeatherByLocationUseCase(WeatherRepositoryImpl(api, converter))
    }
}