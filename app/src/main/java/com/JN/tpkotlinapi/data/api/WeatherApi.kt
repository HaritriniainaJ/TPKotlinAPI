package com.JN.tpkotlinapi.data.api

import com.JN.tpkotlinapi.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("timezone") timezone: String = "auto",
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min"
    ): WeatherResponse
}