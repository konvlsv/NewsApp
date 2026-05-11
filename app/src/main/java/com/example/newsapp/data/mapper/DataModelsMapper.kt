package com.example.newsapp.data.mapper

import com.example.newsapp.data.source.local.models.ArticleLocalModel
import com.example.newsapp.data.source.remote.models.ArticleCategoryRemoteModel
import com.example.newsapp.data.source.remote.models.ArticleQueryRemoteModel
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import com.example.newsapp.domain.models.ArticleCategoryDomainModel
import com.example.newsapp.domain.models.ArticleDomainModel
import com.example.newsapp.domain.models.ArticleQueryDomainModel

object DataModelsMapper {
    fun mapToArticleDomainModel(response: NewsApiResponseDto): List<ArticleDomainModel> {
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

    fun matToArticleQueryRemoteModel(articleQueryDomainModel: ArticleQueryDomainModel): ArticleQueryRemoteModel =
        ArticleQueryRemoteModel(
            query = articleQueryDomainModel.query,
            category = mapToArticleCategoryDomainModel(articleQueryDomainModel.category)
        )

    private fun mapToArticleCategoryDomainModel(articleCategoryDomainModel: ArticleCategoryDomainModel): ArticleCategoryRemoteModel =
        when (articleCategoryDomainModel) {
            ArticleCategoryDomainModel.GENERAL -> ArticleCategoryRemoteModel.GENERAL
            ArticleCategoryDomainModel.BUSINESS -> ArticleCategoryRemoteModel.BUSINESS
            ArticleCategoryDomainModel.ENTERTAINMENT -> ArticleCategoryRemoteModel.ENTERTAINMENT
            ArticleCategoryDomainModel.HEALTH -> ArticleCategoryRemoteModel.HEALTH
            ArticleCategoryDomainModel.SCIENCE -> ArticleCategoryRemoteModel.SCIENCE
            ArticleCategoryDomainModel.SPORTS -> ArticleCategoryRemoteModel.SPORTS
            ArticleCategoryDomainModel.TECHNOLOGY -> ArticleCategoryRemoteModel.TECHNOLOGY
        }

    fun mapToArticleLocalModel(article: ArticleDomainModel): ArticleLocalModel {
        return ArticleLocalModel(
            id = article.id,
            name = article.name,
            author = article.author,
            content = article.content,
            description = article.description,
            publishedAt = article.publishedAt,
            title = article.title,
            url = article.url,
            urlToImage = article.urlToImage
        )
    }

    fun mapToArticleDomainModel(article: ArticleLocalModel): ArticleDomainModel {
        return ArticleDomainModel(
            id = article.id,
            name = article.name,
            author = article.author,
            content = article.content,
            description = article.description,
            publishedAt = article.publishedAt,
            title = article.title,
            url = article.url,
            urlToImage = article.urlToImage
        )
    }
}