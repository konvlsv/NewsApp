package com.example.newsapp.data.repository

import com.example.newsapp.data.mapper.DataModelsMapper
import com.example.newsapp.data.source.remote.RemoteDataSource
import com.example.newsapp.domain.models.ArticleCategoryDomainModel
import com.example.newsapp.domain.models.ArticleDomainModel
import com.example.newsapp.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    val remoteDataSource: RemoteDataSource,
    val mapper: DataModelsMapper
) : ArticleRepository {

    override suspend fun getCategoryTopHeadlines(category: ArticleCategoryDomainModel): List<ArticleDomainModel> {
        return mapper.toDomainModels(
            remoteDataSource.getCategoryTopHeadlines(
                mapper.toRemoteModel(category)
            )
        )
    }

    override suspend fun searchTopHeadlines(
        query: String,
        category: ArticleCategoryDomainModel
    ): List<ArticleDomainModel> {
        return mapper.toDomainModels(
            remoteDataSource.searchTopHeadlines(
                query,
                mapper.toRemoteModel(category)
            )
        )
    }
}