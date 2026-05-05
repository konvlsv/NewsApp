package com.example.newsapp.data.source.remote.api

import com.example.newsapp.data.source.remote.models.NewsApiResponseDto

interface NewsApi {
    suspend fun getCategoryTopHeadlines(
        category: String,
    ): NewsApiResponseDto

    suspend fun searchTopHeadlines(
        query: String,
        category: String,
    ): NewsApiResponseDto
}