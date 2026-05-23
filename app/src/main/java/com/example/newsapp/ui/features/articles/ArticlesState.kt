package com.example.newsapp.ui.features.articles

import com.example.newsapp.ui.common.models.ArticleDisplayModel
import com.example.newsapp.ui.features.articles.models.ArticleQueryDisplayModel

data class ArticlesState(
    val articles: List<ArticleDisplayModel>,
    val articleQuery: ArticleQueryDisplayModel = ArticleQueryDisplayModel(),
    val expandedCards: Set<ArticleDisplayModel> = emptySet(),
    val isRefreshing: Boolean = false,
)