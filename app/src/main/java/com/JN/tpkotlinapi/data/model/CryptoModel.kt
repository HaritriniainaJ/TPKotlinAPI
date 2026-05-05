package com.JN.tpkotlinapi.data.model

import com.google.gson.annotations.SerializedName

data class CryptoResponse(
    @SerializedName("bitcoin") val bitcoin: BitcoinPrice
)

data class BitcoinPrice(
    @SerializedName("usd") val usd: Double
)