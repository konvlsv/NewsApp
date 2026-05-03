package com.example.newsapp.data.source.remote

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.source.remote.api.NewsApiClient
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import java.io.IOException
import java.net.SocketTimeoutException

class RemoteDataSourceImpl(
    val apiService: NewsApiClient
) : RemoteDataSource {
    override suspend fun getArticles(
        query: String,
        category: String,
        country: String
    ): NewsApiResponseDto {
        return try {
            apiService.getArticles(query, category, country)
        } catch (e: SocketTimeoutException) {
            throw RemoteException.TimeoutException()
        } catch (e: IOException) {
            throw RemoteException.NoInternetException()
        } catch (e: Exception) {
            throw RemoteException.UnknownException(e.message ?: "Unknown error")
        }
    }
}