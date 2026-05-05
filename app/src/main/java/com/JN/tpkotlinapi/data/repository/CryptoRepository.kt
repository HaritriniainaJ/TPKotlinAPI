package com.JN.tpkotlinapi.data.repository

import com.JN.tpkotlinapi.data.api.RetrofitInstance

class CryptoRepository {
    private val api = RetrofitInstance.cryptoApi

    suspend fun getBitcoinPrice(): Double {
        return api.getBitcoinPrice().bitcoin.usd
    }
}