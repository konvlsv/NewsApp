package com.example.newsapp.data.source.remote.api

import com.example.newsapp.data.source.remote.models.ArticleQueryRemoteModel
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto

interface NewsApi {
    suspend fun getTopHeadlines(query: ArticleQueryRemoteModel): NewsApiResponseDto
}