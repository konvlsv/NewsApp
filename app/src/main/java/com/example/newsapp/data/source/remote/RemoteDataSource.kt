package com.example.newsapp.data.source.remote

import com.example.newsapp.data.source.remote.models.ArticleQueryRequest
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto

interface RemoteDataSource {
    suspend fun getTopHeadlines(
        query: ArticleQueryRequest
    ): NewsApiResponseDto
}