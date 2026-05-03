package com.example.newsapp.data.mapper

import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import com.example.newsapp.ui.models.ArticleDisplayModel

object ArticleMapper {
    fun toDisplayModel(response: NewsApiResponseDto): List<ArticleDisplayModel> {
        return response.articles?.map { apiArticle ->
            ArticleDisplayModel(
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
}