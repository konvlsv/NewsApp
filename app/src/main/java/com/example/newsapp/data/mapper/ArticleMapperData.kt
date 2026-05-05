package com.example.newsapp.data.mapper

import com.example.newsapp.data.source.remote.models.NewsApiResponseDto
import com.example.newsapp.domain.models.ArticleDomainModel

object ArticleMapperData {
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
}