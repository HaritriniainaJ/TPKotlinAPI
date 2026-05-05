package com.JN.tpkotlinapi.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("articles") val articles: List<Article>
)

data class Article(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("urlToImage") val imageUrl: String?,
    @SerializedName("url") val url: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("source") val source: Source
)

data class Source(@SerializedName("name") val name: String)