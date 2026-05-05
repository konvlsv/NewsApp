package com.example.newsapp.data.source.remote

import com.example.newsapp.data.source.remote.models.ArticleCategoryRemoteModel
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto

interface RemoteDataSource {

    suspend fun getCategoryTopHeadlines(
        category: ArticleCategoryRemoteModel,
    ): NewsApiResponseDto

    suspend fun searchTopHeadlines(
        query: String,
        category: ArticleCategoryRemoteModel,
    ): NewsApiResponseDto
}