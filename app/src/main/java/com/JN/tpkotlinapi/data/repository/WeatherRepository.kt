package com.JN.tpkotlinapi.data.repository

import com.JN.tpkotlinapi.data.api.RetrofitInstance
import com.JN.tpkotlinapi.data.model.WeatherResponse

class WeatherRepository {
    private val api = RetrofitInstance.weatherApi

    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse =
        api.getWeather(lat = lat, lon = lon)
}