package com.example.newsapp.ui.features.articles.models

data class ArticleQueryUi(
    val query: String = "",
    val category: ArticleCategoryUi = ArticleCategoryUi.GENERAL,
)
