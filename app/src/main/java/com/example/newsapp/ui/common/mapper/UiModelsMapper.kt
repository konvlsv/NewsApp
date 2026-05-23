package com.example.newsapp.ui.common.mapper

import com.example.newsapp.domain.models.ArticleCategory
import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.models.ArticleQuery
import com.example.newsapp.ui.features.articles.models.ArticleCategoryUi
import com.example.newsapp.ui.common.models.ArticleUi
import com.example.newsapp.ui.features.articles.models.ArticleQueryUi

object UiModelsMapper {

    fun toArticleQuery(articleQueryUi: ArticleQueryUi): ArticleQuery =
        ArticleQuery(
            query = articleQueryUi.query,
            category = toArticleCategory(articleQueryUi.category)
        )

    fun toArticleUi(domainModels: List<Article>): List<ArticleUi> {
        return domainModels.map { domainModel ->
            toArticleUi(domainModel)
        }
    }

    fun toArticleUi(domainModel: Article): ArticleUi {
        return ArticleUi(
            id = domainModel.id,
            sourceName = domainModel.name,
            author = domainModel.author,
            content = domainModel.content,
            description = domainModel.description,
            publishedAt = domainModel.publishedAt,
            title = domainModel.title,
            articleUrl = domainModel.url,
            imageUrl = domainModel.urlToImage,
        )
    }

    fun toArticle(article: ArticleUi): Article {
        return Article(
            id = article.id,
            name = article.sourceName,
            author = article.author,
            content = article.content,
            description = article.description,
            publishedAt = article.publishedAt,
            title = article.title,
            url = article.articleUrl,
            urlToImage = article.imageUrl,
        )
    }

    private fun toArticleCategory(articleCategoryUi: ArticleCategoryUi): ArticleCategory =
        when (articleCategoryUi) {
            ArticleCategoryUi.GENERAL -> ArticleCategory.GENERAL
            ArticleCategoryUi.BUSINESS -> ArticleCategory.BUSINESS
            ArticleCategoryUi.ENTERTAINMENT -> ArticleCategory.ENTERTAINMENT
            ArticleCategoryUi.HEALTH -> ArticleCategory.HEALTH
            ArticleCategoryUi.SCIENCE -> ArticleCategory.SCIENCE
            ArticleCategoryUi.SPORTS -> ArticleCategory.SPORTS
            ArticleCategoryUi.TECHNOLOGY -> ArticleCategory.TECHNOLOGY
        }
}