package com.example.newsapp.data.source.remote

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.source.remote.models.NewsApiResponse
import java.io.IOException
import java.net.SocketTimeoutException

class RemoteRepositoryImpl(
    val apiService: NewsApiService
) : RemoteRepository {
    override suspend fun getArticles(
        query: String,
        category: String,
        country: String
    ): NewsApiResponse {
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