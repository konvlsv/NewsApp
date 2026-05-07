package com.example.newsapp.ui.state

import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.models.ArticleQueryDisplayModel

sealed class NewsUiState {
    object Loading : NewsUiState()

    data class Success(
        val articles: List<ArticleDisplayModel>,
        val articleQuery: ArticleQueryDisplayModel = ArticleQueryDisplayModel(),
        val expandedCards: Set<ArticleDisplayModel> = emptySet(),
        val isRefreshing: Boolean = false,
    ) : NewsUiState()

    data class Error(
        val message: String,
        val errorType: ErrorType = ErrorType.GENERIC
    ) : NewsUiState()
}