package com.example.newsapp.ui.models

data class ArticleQueryDisplayModel(
    val query: String = "",
    val category: ArticleCategoryDisplayModel = ArticleCategoryDisplayModel.GENERAL,
)
