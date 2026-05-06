package com.example.newsapp.data.source.remote.api

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.source.remote.models.ArticleQueryRemoteModel
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import com.example.newsapp.data.source.remote.models.getMockNewsApiResponse
import kotlinx.coroutines.delay

object NewsApiClient : NewsApi {
    override suspend fun getTopHeadlines(query: ArticleQueryRemoteModel): NewsApiResponseDto {
        delay(2000)
        val response = getMockNewsApiResponse()
        if (!response.status.equals("ok")) {
            throw RemoteException.ServerException(response.status ?: "server error")
        }
        return response
    }
}