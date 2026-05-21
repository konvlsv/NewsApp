package com.example.newsapp.data.source.remote.api

import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String,
        @Query("q") searchQuery: String? = null,
        @Query("country") country: String? = null,
        @Query("language") language: String? = null,
    ): NewsApiResponseDto
}