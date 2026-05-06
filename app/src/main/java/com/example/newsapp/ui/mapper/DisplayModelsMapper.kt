package com.example.newsapp.ui.mapper

import com.example.newsapp.domain.models.ArticleCategoryDomainModel
import com.example.newsapp.domain.models.ArticleDomainModel
import com.example.newsapp.domain.models.ArticleQueryDomainModel
import com.example.newsapp.ui.models.ArticleCategoryDisplayModel
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.models.ArticleQueryDisplayModel

object DisplayModelsMapper {

    fun mapToArticleQueryDomainModel(articleQueryDisplayModel: ArticleQueryDisplayModel): ArticleQueryDomainModel =
        ArticleQueryDomainModel(
            query = articleQueryDisplayModel.query,
            category = mapToArticleCategoryDomainModel(articleQueryDisplayModel.category)
        )

    fun mapToArticleDisplayModel(domainModels: List<ArticleDomainModel>): List<ArticleDisplayModel> {
        return domainModels.map { domainModel ->
            ArticleDisplayModel(
                id = domainModel.id,
                name = domainModel.name,
                author = domainModel.author,
                content = domainModel.content,
                description = domainModel.description,
                publishedAt = domainModel.publishedAt,
                title = domainModel.title,
                url = domainModel.url,
                urlToImage = domainModel.urlToImage,
            )
        }
    }

    private fun mapToArticleCategoryDomainModel(articleCategoryDisplayModel: ArticleCategoryDisplayModel): ArticleCategoryDomainModel =
        when (articleCategoryDisplayModel) {
            ArticleCategoryDisplayModel.GENERAL -> ArticleCategoryDomainModel.GENERAL
            ArticleCategoryDisplayModel.BUSINESS -> ArticleCategoryDomainModel.BUSINESS
            ArticleCategoryDisplayModel.ENTERTAINMENT -> ArticleCategoryDomainModel.ENTERTAINMENT
            ArticleCategoryDisplayModel.HEALTH -> ArticleCategoryDomainModel.HEALTH
            ArticleCategoryDisplayModel.SCIENCE -> ArticleCategoryDomainModel.SCIENCE
            ArticleCategoryDisplayModel.SPORTS -> ArticleCategoryDomainModel.SPORTS
            ArticleCategoryDisplayModel.TECHNOLOGY -> ArticleCategoryDomainModel.TECHNOLOGY
        }
}