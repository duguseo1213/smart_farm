package com.wcd.farm.di

import android.content.Context
import com.wcd.farm.data.remote.WeatherApi
import com.wcd.farm.data.remote.WeatherApiClient
import com.wcd.farm.data.repository.DiseaseRepository
import com.wcd.farm.data.repository.MemorialRepository
import com.wcd.farm.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return WeatherApiClient.weatherApi
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: WeatherApi): WeatherRepository = WeatherRepository(
        provideWeatherApi()
    )

    @Provides
    @Singleton
    fun provideDiseaseRepository(): DiseaseRepository {
        return DiseaseRepository()
    }

    @Provides
    @Singleton
    fun provideMemorialRepository(): MemorialRepository {
        return MemorialRepository()
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}