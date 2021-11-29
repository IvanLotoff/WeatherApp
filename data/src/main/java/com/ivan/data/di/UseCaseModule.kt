package com.ivan.data.di

import com.ivan.data.api.WeatherApi
import com.ivan.data.converter.WeatherConverter
import com.ivan.data.repository.WeatherRepositoryImpl
import com.ivan.domain.usecase.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class UseCaseModule {
    @Provides
    fun weatherUseCase(api: WeatherApi, converter: WeatherConverter): WeatherUseCase = WeatherUseCase(WeatherRepositoryImpl(api, converter))
}