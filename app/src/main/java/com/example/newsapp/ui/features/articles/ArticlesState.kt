package com.example.newsapp.ui.features.articles

import com.example.newsapp.ui.common.models.ArticleUi
import com.example.newsapp.ui.features.articles.models.ArticleQueryUi

data class ArticlesState(
    val articles: List<ArticleUi>,
    val articleQuery: ArticleQueryUi = ArticleQueryUi(),
    val isRefreshing: Boolean = false,
)