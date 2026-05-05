package com.JN.tpkotlinapi.data.repository

import com.JN.tpkotlinapi.data.api.RetrofitInstance
import com.JN.tpkotlinapi.data.model.NewsResponse

class NewsRepository {
    private val api = RetrofitInstance.newsApi
    private val apiKey = "VOTRE_CLE_NEWSAPI"
    suspend fun getTopHeadlines(country: String = "fr"): NewsResponse =
        api.getTopHeadlines(country = country, apiKey = apiKey)
}