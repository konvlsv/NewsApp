package com.example.newsapp.data.source.network.models

data class NewsApiResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)