package com.JN.tpkotlinapi.data.repository

import com.JN.tpkotlinapi.data.api.RetrofitInstance
import com.JN.tpkotlinapi.data.model.ExchangeResponse

class CurrencyRepository {
    private val api = RetrofitInstance.currencyApi
    private val apiKey = "3a8356f6ec380387c0ad5696"

    suspend fun getRates(base: String = "USD"): ExchangeResponse {
        return api.getLatestRates(apiKey = apiKey, base = base)
    }
}