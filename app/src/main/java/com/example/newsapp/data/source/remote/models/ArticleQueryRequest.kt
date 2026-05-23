package com.example.newsapp.data.source.remote.models

data class ArticleQueryRequest(
    val query: String = "",
    val category: ArticleCategoryRequest,
)