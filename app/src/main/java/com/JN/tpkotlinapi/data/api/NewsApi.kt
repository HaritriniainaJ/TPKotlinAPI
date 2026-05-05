package com.JN.tpkotlinapi.data.api

import com.JN.tpkotlinapi.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/everything")
    suspend fun getTopHeadlines(
        @Query("q") query: String = "france",
        @Query("language") language: String = "fr",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}