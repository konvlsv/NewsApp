package com.example.newsapp.data.mapper

import com.example.newsapp.data.source.local.models.ArticleEntity
import com.example.newsapp.data.source.remote.models.ArticleCategoryRequest
import com.example.newsapp.data.source.remote.models.ArticleQueryRequest
import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import com.example.newsapp.domain.models.ArticleCategory
import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.models.ArticleQuery

object DataModelsMapper {
    fun toArticle(response: NewsApiResponseDto): List<Article> {
        return response.articles?.map { apiArticle ->
            Article(
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

    fun toArticleQueryRequest(articleQuery: ArticleQuery): ArticleQueryRequest =
        ArticleQueryRequest(
            query = articleQuery.query,
            category = toArticleCategoryRequest(articleQuery.category)
        )

    private fun toArticleCategoryRequest(articleCategory: ArticleCategory): ArticleCategoryRequest =
        when (articleCategory) {
            ArticleCategory.GENERAL -> ArticleCategoryRequest.GENERAL
            ArticleCategory.BUSINESS -> ArticleCategoryRequest.BUSINESS
            ArticleCategory.ENTERTAINMENT -> ArticleCategoryRequest.ENTERTAINMENT
            ArticleCategory.HEALTH -> ArticleCategoryRequest.HEALTH
            ArticleCategory.SCIENCE -> ArticleCategoryRequest.SCIENCE
            ArticleCategory.SPORTS -> ArticleCategoryRequest.SPORTS
            ArticleCategory.TECHNOLOGY -> ArticleCategoryRequest.TECHNOLOGY
        }

    fun toArticleEntity(article: Article): ArticleEntity {
        return ArticleEntity(
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

    fun toArticle(article: ArticleEntity): Article {
        return Article(
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