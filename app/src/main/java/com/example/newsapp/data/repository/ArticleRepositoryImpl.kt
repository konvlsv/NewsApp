package com.example.newsapp.data.repository

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.source.remote.mapper.ArticleRemoteMapper
import com.example.newsapp.data.source.remote.RemoteDataSource
import com.example.newsapp.ui.models.ArticleDisplayModel

class ArticleRepositoryImpl(
    val remoteDataSource: RemoteDataSource,
    val mapper: ArticleRemoteMapper
) : ArticleRepository {

    override suspend fun getArticles(
        query: String,
        category: String,
        country: String
    ): List<ArticleDisplayModel> {
        return try {
            val response = remoteDataSource.getArticles(query, category, country)
            mapper.toDisplayModel(response)
        } catch (e: Exception) {
            throw RemoteException.ParseException()
        }
    }
}