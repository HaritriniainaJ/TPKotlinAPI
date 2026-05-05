package com.JN.tpkotlinapi.data.model

import com.google.gson.annotations.SerializedName

data class ExchangeResponse(
    @SerializedName("result") val result: String,
    @SerializedName("base_code") val baseCode: String,
    @SerializedName("time_last_update_utc") val lastUpdate: String,
    @SerializedName("conversion_rates") val conversionRates: Map<String, Double>
)