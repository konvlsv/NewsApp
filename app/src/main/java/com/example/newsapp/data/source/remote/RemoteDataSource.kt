package com.example.newsapp.data.source.remote

import com.example.newsapp.data.source.remote.models.NewsApiResponseDto

interface RemoteDataSource {

    suspend fun getCategoryTopHeadlines(
        category: String,
    ): NewsApiResponseDto

    suspend fun searchTopHeadlines(
        query: String,
        category: String,
    ): NewsApiResponseDto
}