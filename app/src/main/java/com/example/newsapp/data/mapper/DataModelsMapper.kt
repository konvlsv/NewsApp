package com.example.newsapp.data.mapper

import com.example.newsapp.data.source.remote.models.ArticleCategoryRemoteModel
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import com.example.newsapp.domain.models.ArticleCategoryDomainModel
import com.example.newsapp.domain.models.ArticleDomainModel

object DataModelsMapper {
    fun toDomainModels(response: NewsApiResponseDto): List<ArticleDomainModel> {
        return response.articles?.map { apiArticle ->
            ArticleDomainModel(
                id = apiArticle.source?.id ?: "",
                name = apiArticle.source?.name ?: "",
                author = apiArticle.author ?: "",
                content = apiArticle.content ?: "",
                description = apiArticle.description ?: "",
                publishedAt = apiArticle.publishedAt ?: "",
                title = apiArticle.title ?: "",
                url = apiArticle.url ?: "",
                urlToImage = apiArticle.urlToImage ?: "",
            )
        } ?: emptyList()
    }

    fun toRemoteModel(articleCategoryDomainModel: ArticleCategoryDomainModel): ArticleCategoryRemoteModel =
        when (articleCategoryDomainModel) {
            ArticleCategoryDomainModel.GENERAL -> ArticleCategoryRemoteModel.GENERAL
            ArticleCategoryDomainModel.BUSINESS -> ArticleCategoryRemoteModel.BUSINESS
            ArticleCategoryDomainModel.ENTERTAINMENT -> ArticleCategoryRemoteModel.ENTERTAINMENT
            ArticleCategoryDomainModel.HEALTH -> ArticleCategoryRemoteModel.HEALTH
            ArticleCategoryDomainModel.SCIENCE -> ArticleCategoryRemoteModel.SCIENCE
            ArticleCategoryDomainModel.SPORTS -> ArticleCategoryRemoteModel.SPORTS
            ArticleCategoryDomainModel.TECHNOLOGY -> ArticleCategoryRemoteModel.TECHNOLOGY
        }
}