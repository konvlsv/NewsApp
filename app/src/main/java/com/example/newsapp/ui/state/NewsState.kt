package com.example.newsapp.ui.state

import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.models.ArticleQueryDisplayModel

data class NewsState(
    val articles: List<ArticleDisplayModel>,
    val articleQuery: ArticleQueryDisplayModel = ArticleQueryDisplayModel(),
    val expandedCards: Set<ArticleDisplayModel> = emptySet(),
    val isRefreshing: Boolean = false,
)