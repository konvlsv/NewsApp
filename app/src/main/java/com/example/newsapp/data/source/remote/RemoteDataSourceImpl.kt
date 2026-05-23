package com.example.newsapp.data.source.remote

import android.util.Log
import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.source.remote.api.NewsApi
import com.example.newsapp.data.source.remote.models.ArticleQueryRequest
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.SocketTimeoutException

class RemoteDataSourceImpl(
    val newsApi: NewsApi
) : RemoteDataSource {
    private val prettyJson = Json { prettyPrint = true }
    override suspend fun getTopHeadlines(query: ArticleQueryRequest): NewsApiResponseDto {
        return try {
            newsApi.getTopHeadlines(
                category = query.category.name.lowercase(),
                searchQuery = query.query.ifBlank { null },
            ).also { responseDto ->
                val formattedLog = prettyJson.encodeToString(responseDto)
                Log.d("RemoteDataSourceImpl", "getTopHeadlines:\n$formattedLog")
            }
        } catch (e: SocketTimeoutException) {
            throw RemoteException.TimeoutException()
        } catch (e: IOException) {
            throw RemoteException.NoInternetException()
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException) throw e
            throw RemoteException.UnknownException(e.message ?: "Unknown error")
        }
    }
}