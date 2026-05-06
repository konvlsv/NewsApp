package com.example.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.App
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.ui.mapper.DisplayModelsMapper
import com.example.newsapp.ui.models.ArticleCategoryDisplayModel
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.models.ArticleQueryDisplayModel
import com.example.newsapp.ui.state.NewsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    val getTopHeadlinesUseCase: GetTopHeadlinesUseCase = App.instance.getTopHeadlinesUseCase,
    val mapper: DisplayModelsMapper = App.instance.displayModelsMapper
) : ViewModel() {

    private val _uiState: MutableStateFlow<NewsUiState> = MutableStateFlow(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = NewsUiState.Loading
        viewModelScope.launch {
            loadArticles()
        }
    }

    fun setDetailsArticle(article: ArticleDisplayModel) {
        _uiState.update { currentState ->
            when (currentState) {
                is NewsUiState.Success -> currentState.copy(detailsArticle = article)
                is NewsUiState.Loading -> currentState
                is NewsUiState.Error -> currentState
            }
        }
    }

    fun onArticleSelectedCategoryChange(category: ArticleCategoryDisplayModel) {
        _uiState.update { currentState ->
            when (currentState) {
                is NewsUiState.Success -> currentState.copy(
                    articleQuery = currentState.articleQuery.copy(
                        category = category
                    )
                )

                is NewsUiState.Loading -> currentState
                is NewsUiState.Error -> currentState
            }
        }
    }

    fun onArticleSearchBarValueChange(query: String) {
        _uiState.update { currentState ->
            when (currentState) {
                is NewsUiState.Success -> currentState.copy(
                    articleQuery = currentState.articleQuery.copy(
                        query = query
                    )
                )

                is NewsUiState.Loading -> currentState
                is NewsUiState.Error -> currentState
            }
        }
    }

    fun onArticleSearchBarDeleteClick() {
        _uiState.update { currentState ->
            when (currentState) {
                is NewsUiState.Success -> currentState.copy(
                    articleQuery = currentState.articleQuery.copy(
                        query = ""
                    )
                )

                is NewsUiState.Loading -> currentState
                is NewsUiState.Error -> currentState
            }
        }
    }

    fun onExpandOrCollapseCardClick(article: ArticleDisplayModel) {
        _uiState.update { currentState ->
            when (currentState) {
                is NewsUiState.Loading -> currentState
                is NewsUiState.Error -> currentState
                is NewsUiState.Success -> {
                    val newCards = currentState.expandedCards.toMutableSet()
                    if (newCards.contains(article)) {
                        newCards.remove(article)
                    } else {
                        newCards.add(article)
                    }
                    currentState.copy(expandedCards = newCards)
                }
            }
        }
    }

    fun onShareClick(article: ArticleDisplayModel) {

    }

    fun onOpenInBrowserClick(article: ArticleDisplayModel) {

    }

    fun onArticleSearchBarSearchClick() {
        onRefresh()
    }

    fun onRefresh() {
        viewModelScope.launch {
            loadArticles()
        }
    }

    private suspend fun loadArticles() {
        try {
            val previousSuccess = _uiState.value as? NewsUiState.Success
            val previousQuery = previousSuccess?.articleQuery ?: ArticleQueryDisplayModel()
            val newArticles = mapper.mapToArticleDisplayModel(
                getTopHeadlinesUseCase(
                    mapper.mapToArticleQueryDomainModel(previousQuery)
                )
            )
            _uiState.value = NewsUiState.Success(
                articles = newArticles,
                articleQuery = previousQuery,
                expandedCards = previousSuccess?.expandedCards ?: emptySet(),
                detailsArticle = previousSuccess?.detailsArticle,
                isRefreshing = false
            )
        } catch (e: Exception) {
            _uiState.value = NewsUiState.Error(
                message = e.message ?: "Unknown error"
            )
        }
    }
}