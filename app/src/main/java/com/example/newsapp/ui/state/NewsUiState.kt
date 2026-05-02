package com.example.newsapp.ui.state

import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.models.getMockArticleUiList

sealed class NewsUiState {
    object Loading : NewsUiState()

    data class Success(
        val articles: List<ArticleDisplayModel>,
        val searchQuery: String = "",
        val selectedCategory: ArticleCategory = ArticleCategory.GENERAL,
        val expandedCards: Set<ArticleDisplayModel> = emptySet(),
        val detailsArticle: ArticleDisplayModel? = null,
    ) : NewsUiState()

    data class Error(
        val message: String,
        val errorType: ErrorType = ErrorType.GENERIC
    ) : NewsUiState()
}

fun getMockSuccessNewsUiState(): NewsUiState.Success {
    return NewsUiState.Success(
        articles = getMockArticleUiList(),
        searchQuery = "",
        selectedCategory = ArticleCategory.GENERAL,
        expandedCards = emptySet(),
        detailsArticle = null
    )
}