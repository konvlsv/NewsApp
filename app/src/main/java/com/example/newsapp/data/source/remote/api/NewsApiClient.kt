package com.example.newsapp.data.source.remote.api

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import com.example.newsapp.data.source.remote.models.getMockNewsApiResponse

object NewsApiClient {
    suspend fun getArticles(
        query: String,
        category: String,
        country: String,
    ): NewsApiResponseDto {
        val response = getMockNewsApiResponse()
        if (!response.status.equals("ok")) {
            throw RemoteException.ServerException(response.status ?: "server error")
        }
        return response
    }
}