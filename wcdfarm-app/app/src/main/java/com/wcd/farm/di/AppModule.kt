package com.wcd.farm.di

import android.content.Context
import android.content.SharedPreferences
import com.wcd.farm.data.remote.AuthApi
import com.wcd.farm.data.remote.DeviceApi
import com.wcd.farm.data.remote.GardenApi
import com.wcd.farm.data.remote.HarmApi
import com.wcd.farm.data.remote.ServerClient
import com.wcd.farm.data.remote.UserApi
import com.wcd.farm.data.remote.WeatherApi
import com.wcd.farm.data.remote.WeatherApiClient
import com.wcd.farm.data.repository.DiseaseRepository
import com.wcd.farm.data.repository.MemorialRepository
import com.wcd.farm.data.repository.ServerRepository
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
    fun provideAuthApi(): AuthApi {
        return ServerClient.authApi
    }

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return WeatherApiClient.weatherApi
    }

    @Provides
    @Singleton
    fun provideGardenApi(): GardenApi {
        return ServerClient.gardenApi
    }

    @Provides
    @Singleton
    fun provideHarmApi(): HarmApi {
        return ServerClient.HarmApi
    }

    @Provides
    @Singleton
    fun provideUserApi(): UserApi {
        return ServerClient.userApi
    }

    @Provides
    @Singleton
    fun provideDeviceApi(): DeviceApi {
        return ServerClient.deviceApi
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideServerRepository(authApi: AuthApi, sharedPreferences: SharedPreferences): ServerRepository {
        return ServerRepository(authApi, sharedPreferences)
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