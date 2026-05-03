package com.example.newsapp.data.source.remote

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.source.remote.models.NewsApiResponse
import com.example.newsapp.data.source.remote.models.getMockNewsApiResponse

object NewsApiService {
    suspend fun getArticles(
        query: String,
        category: String,
        country: String,
    ): NewsApiResponse {
        val response = getMockNewsApiResponse()
        if (!response.status.equals("ok")) {
            throw RemoteException.ServerException(response.status ?: "server error")
        }
        return response
    }
}