package com.example.newsapp.data.repository

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.mapper.DataModelsMapper
import com.example.newsapp.data.source.remote.RemoteDataSource
import com.example.newsapp.domain.models.ArticleDomainModel
import com.example.newsapp.domain.models.ArticleQueryDomainModel
import com.example.newsapp.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    val remoteDataSource: RemoteDataSource,
    val mapper: DataModelsMapper
) : ArticleRepository {
    override suspend fun getTopHeadlines(query: ArticleQueryDomainModel): List<ArticleDomainModel> {
        return try {
            val remoteQuery = mapper.matToArticleQueryRemoteModel(query)
            val response = remoteDataSource.getTopHeadlines(remoteQuery)
            mapper.mapToArticleDomainModel(response)
        } catch (e: Exception) {
            throw RemoteException.ParseException()
        }
    }
}