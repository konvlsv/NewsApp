package com.example.newsapp.ui.state

import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.models.ArticleQueryDisplayModel

sealed class UiState<T> {
    class Loading<T> : UiState<T>()

    data class Success<T>(val data: T) : UiState<T>()

    data class Error<T>(
        val message: String,
        val errorType: ErrorType = ErrorType.GENERIC
    ) : UiState<T>()
}

data class NewsScreenData(
    val articles: List<ArticleDisplayModel>,
    val articleQuery: ArticleQueryDisplayModel = ArticleQueryDisplayModel(),
    val expandedCards: Set<ArticleDisplayModel> = emptySet(),
    val isRefreshing: Boolean = false,
)

data class DetailsScreenData(
    val detail: ArticleDisplayModel,
)