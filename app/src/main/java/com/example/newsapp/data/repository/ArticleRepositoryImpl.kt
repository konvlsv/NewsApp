package com.example.newsapp.data.repository

import com.example.newsapp.data.mapper.ArticleMapperData
import com.example.newsapp.data.source.remote.RemoteDataSource
import com.example.newsapp.domain.models.ArticleDomainModel
import com.example.newsapp.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    val remoteDataSource: RemoteDataSource,
    val mapper: ArticleMapperData
) : ArticleRepository {

    override suspend fun getCategoryTopHeadlines(category: String): List<ArticleDomainModel> {
        return mapper.toDomainModels(remoteDataSource.getCategoryTopHeadlines(category))
    }

    override suspend fun searchTopHeadlines(
        query: String,
        category: String
    ): List<ArticleDomainModel> {
        return mapper.toDomainModels(remoteDataSource.searchTopHeadlines(query,category))
    }
}