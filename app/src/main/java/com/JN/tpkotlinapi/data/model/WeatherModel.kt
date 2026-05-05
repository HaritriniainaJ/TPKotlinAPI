package com.JN.tpkotlinapi.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("current_weather") val currentWeather: CurrentWeather
)

data class CurrentWeather(
    @SerializedName("temperature") val temperature: Double,
    @SerializedName("windspeed") val windspeed: Double,
    @SerializedName("winddirection") val windDirection: Int,
    @SerializedName("weathercode") val weatherCode: Int,
    @SerializedName("time") val time: String
)

// Convertit le code WMO en description + emoji
fun weatherCodeToInfo(code: Int): Pair<String, String> = when (code) {
    0 -> "Ciel dégagé" to "☀️"
    in 1..3 -> "Partiellement nuageux" to "⛅"
    in 45..48 -> "Brouillard" to "🌫️"
    in 51..57 -> "Bruine" to "🌦️"
    in 61..67 -> "Pluie" to "🌧️"
    in 71..77 -> "Neige" to "❄️"
    in 80..82 -> "Averses" to "🌨️"
    in 95..99 -> "Orage" to "⛈️"
    else -> "Variable" to "🌈"
}