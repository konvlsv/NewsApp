package com.example.newsapp.data.source.remote

import com.example.newsapp.data.source.remote.models.NewsApiResponse

interface RemoteRepository {

    suspend fun getArticles(
        query: String,
        category: String,
        country: String
    ): NewsApiResponse
}