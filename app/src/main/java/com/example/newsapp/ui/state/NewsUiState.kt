package com.example.newsapp.ui.state

import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleDisplayModel

sealed class NewsUiState {
    object Loading : NewsUiState()

    data class Success(
        val articles: List<ArticleDisplayModel>,
        val searchQuery: String = "",
        val selectedCategory: ArticleCategory = ArticleCategory.GENERAL,
        val expandedCards: Set<ArticleDisplayModel> = emptySet(),
        val detailsArticle: ArticleDisplayModel? = null,
        val isRefreshing: Boolean = false,
    ) : NewsUiState()

    data class Error(
        val message: String,
        val errorType: ErrorType = ErrorType.GENERIC
    ) : NewsUiState()
}