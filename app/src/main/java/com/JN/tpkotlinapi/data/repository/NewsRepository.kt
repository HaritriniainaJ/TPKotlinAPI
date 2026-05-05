package com.JN.tpkotlinapi.data.repository

import com.JN.tpkotlinapi.data.api.RetrofitInstance
import com.JN.tpkotlinapi.data.model.NewsResponse

class NewsRepository {
    private val api = RetrofitInstance.newsApi
    private val apiKey = "c81cd4c7947d42b99755e7cb102d544a"

    suspend fun getTopHeadlines(): NewsResponse =
        api.getTopHeadlines(apiKey = apiKey)
}