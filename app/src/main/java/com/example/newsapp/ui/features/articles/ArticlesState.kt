package com.example.newsapp.ui.features.articles

import com.example.newsapp.ui.common.models.ArticleUi
import com.example.newsapp.ui.features.articles.models.ArticleCategoryUi
import com.example.newsapp.ui.state.ErrorState

data class ArticlesState(
    val articles: List<ArticleUi> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val selectedCategory: ArticleCategoryUi = ArticleCategoryUi.GENERAL,
    val expandedArticleUrls: Set<String> = emptySet(),
    val errorState: ErrorState? = null,
){
    val isError: Boolean get() = errorState != null
}