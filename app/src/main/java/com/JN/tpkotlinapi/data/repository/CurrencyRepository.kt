package com.JN.tpkotlinapi.data.repository

import com.JN.tpkotlinapi.data.api.RetrofitInstance
import com.JN.tpkotlinapi.data.model.ExchangeResponse

class CurrencyRepository {

    private val api = RetrofitInstance.currencyApi

    // Remplace par ta vraie clé ExchangeRate-API
    private val apiKey = "VOTRE_CLE_EXCHANGERATE_API"

    suspend fun getRates(base: String = "USD"): ExchangeResponse {
        return api.getLatestRates(apiKey = apiKey, base = base)
    }
}