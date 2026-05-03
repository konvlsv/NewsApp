package com.example.newsapp.data.source.remote.api

import com.example.newsapp.data.source.remote.models.NewsApiResponseDto

interface NewsApi {
    suspend fun getArticles(query: String, category: String, country: String): NewsApiResponseDto
}