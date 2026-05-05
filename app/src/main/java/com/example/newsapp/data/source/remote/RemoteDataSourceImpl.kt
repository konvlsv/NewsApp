package com.example.newsapp.data.source.remote

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.source.remote.api.NewsApi
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import java.io.IOException
import java.net.SocketTimeoutException

class RemoteDataSourceImpl(
    val newsApi: NewsApi
) : RemoteDataSource {
    override suspend fun searchTopHeadlines(
        query: String,
        category: String,
    ): NewsApiResponseDto {
        return try {
            newsApi.searchTopHeadlines(query, category)
        } catch (e: SocketTimeoutException) {
            throw RemoteException.TimeoutException()
        } catch (e: IOException) {
            throw RemoteException.NoInternetException()
        } catch (e: Exception) {
            throw RemoteException.UnknownException(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCategoryTopHeadlines(category: String): NewsApiResponseDto {
        return try {
            newsApi.getCategoryTopHeadlines(category)
        } catch (e: SocketTimeoutException) {
            throw RemoteException.TimeoutException()
        } catch (e: IOException) {
            throw RemoteException.NoInternetException()
        } catch (e: Exception) {
            throw RemoteException.UnknownException(e.message ?: "Unknown error")
        }
    }
}