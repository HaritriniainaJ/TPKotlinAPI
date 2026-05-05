package com.JN.tpkotlinapi.data.api

import com.JN.tpkotlinapi.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "fr",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}