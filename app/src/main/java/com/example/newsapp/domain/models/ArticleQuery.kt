package com.example.newsapp.domain.models

data class ArticleQuery(
    val query: String,
    val category: ArticleCategory,
)