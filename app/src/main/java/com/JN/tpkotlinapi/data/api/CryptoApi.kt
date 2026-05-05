package com.JN.tpkotlinapi.data.api

import com.JN.tpkotlinapi.data.model.CryptoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {
    @GET("api/v3/simple/price")
    suspend fun getBitcoinPrice(
        @Query("ids") ids: String = "bitcoin",
        @Query("vs_currencies") vsCurrencies: String = "usd"
    ): CryptoResponse
}