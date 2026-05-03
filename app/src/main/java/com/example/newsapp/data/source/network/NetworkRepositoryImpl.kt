package com.example.newsapp.data.source.network

import com.example.newsapp.data.source.network.models.NewsApiResponse
import com.example.newsapp.data.source.network.models.getMockNewsApiResponse

class NetworkRepositoryImpl : NetworkRepository {
    override suspend fun getArticles(
        query: String,
        category: String
    ): NewsApiResponse {
        return getMockNewsApiResponse()
    }
}