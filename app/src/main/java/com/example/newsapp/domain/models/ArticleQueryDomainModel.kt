package com.example.newsapp.domain.models

data class ArticleQueryDomainModel(
    val query: String,
    val category: ArticleCategoryDomainModel,
)