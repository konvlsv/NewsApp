package com.example.newsapp.data.source.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class NewsApiResponseDto(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleDto>?
)

@Serializable
data class ArticleDto(
    val source: SourceDto?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

@Serializable
data class SourceDto(
    val id: String?,
    val name: String?
)