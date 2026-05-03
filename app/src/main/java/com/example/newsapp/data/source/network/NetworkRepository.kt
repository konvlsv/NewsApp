package com.example.newsapp.data.source.network

import com.example.newsapp.data.source.network.models.NewsApiResponse

interface NetworkRepository {

    suspend fun getArticles(
        query: String,
        category: String
    ): NewsApiResponse
}