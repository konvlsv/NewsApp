package com.example.newsapp.data.source.remote.models

data class ArticleQueryRemoteModel(
    val query: String = "",
    val category: ArticleCategoryRemoteModel,
)