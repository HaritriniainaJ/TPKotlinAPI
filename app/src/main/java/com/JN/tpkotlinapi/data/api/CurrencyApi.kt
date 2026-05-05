package com.JN.tpkotlinapi.data.api

import com.JN.tpkotlinapi.data.model.ExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("v6/{apiKey}/latest/{base}")
    suspend fun getLatestRates(
        @Path("apiKey") apiKey: String,
        @Path("base") base: String = "USD"
    ): ExchangeResponse
}