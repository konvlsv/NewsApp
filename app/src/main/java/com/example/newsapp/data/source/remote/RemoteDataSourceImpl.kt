package com.example.newsapp.data.source.remote

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.source.remote.api.NewsApi
import com.example.newsapp.data.source.remote.models.ArticleQueryRemoteModel
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import java.io.IOException
import java.net.SocketTimeoutException

class RemoteDataSourceImpl(
    val newsApi: NewsApi
) : RemoteDataSource {
    override suspend fun getTopHeadlines(query: ArticleQueryRemoteModel): NewsApiResponseDto {
        return try {
            newsApi.getTopHeadlines(query)
        } catch (e: SocketTimeoutException) {
            throw RemoteException.TimeoutException()
        } catch (e: IOException) {
            throw RemoteException.NoInternetException()
        } catch (e: Exception) {
            throw RemoteException.UnknownException(e.message ?: "Unknown error")
        }
    }
}