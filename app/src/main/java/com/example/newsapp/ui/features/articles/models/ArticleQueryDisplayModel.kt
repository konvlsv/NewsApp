package com.example.newsapp.ui.features.articles.models

data class ArticleQueryDisplayModel(
    val query: String = "",
    val category: ArticleCategoryDisplayModel = ArticleCategoryDisplayModel.GENERAL,
)
