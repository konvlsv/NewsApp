package com.example.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.App
import com.example.newsapp.domain.exception.DomainException
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.ui.mapper.DisplayModelsMapper
import com.example.newsapp.ui.models.ArticleCategoryDisplayModel
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.models.ArticleQueryDisplayModel
import com.example.newsapp.ui.state.ErrorType
import com.example.newsapp.ui.state.NewsUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class NewsViewModel(
    val getTopHeadlinesUseCase: GetTopHeadlinesUseCase = App.instance.getTopHeadlinesUseCase,
    val mapper: DisplayModelsMapper = App.instance.displayModelsMapper
) : ViewModel() {

    private val _uiState: MutableStateFlow<NewsUiState> = MutableStateFlow(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        _uiState.update { NewsUiState.Loading }
        loadArticles()
    }

    fun onArticleSelectedCategoryChange(category: ArticleCategoryDisplayModel) {
        _uiState.update { currentState ->
            if (currentState is NewsUiState.Success) {
                currentState.copy(
                    articleQuery = currentState.articleQuery.copy(category = category),
                    isRefreshing = true
                )
            } else currentState
        }
        loadArticles(true)
    }

    fun onArticleSearchBarValueChange(query: String) {
        _uiState.update { currentState ->
            if (currentState is NewsUiState.Success) {
                currentState.copy(
                    articleQuery = currentState.articleQuery.copy(query = query),
                    isRefreshing = true
                )
            } else currentState
        }
        loadArticles(true)
    }

    fun onArticleSearchBarDeleteClick() {
        _uiState.update { currentState ->
            if (currentState is NewsUiState.Success) {
                currentState.copy(
                    articleQuery = currentState.articleQuery.copy(query = ""),
                    isRefreshing = true
                )
            } else currentState
        }
        loadArticles(true)
    }

    fun onExpandOrCollapseCardClick(article: ArticleDisplayModel) {
        _uiState.update { currentState ->
            if (currentState is NewsUiState.Success) {
                val newCards = currentState.expandedCards.toMutableSet()
                if (newCards.contains(article)) {
                    newCards.remove(article)
                } else {
                    newCards.add(article)
                }
                currentState.copy(expandedCards = newCards)
            } else currentState
        }
    }

    fun onArticleSearchBarSearchClick() {
        onRefresh()
    }

    fun onRefresh() {
        _uiState.update { currentState ->
            if (currentState is NewsUiState.Success) {
                currentState.copy(isRefreshing = true)
            } else currentState
        }
        loadArticles()
    }

    private fun loadArticles(withDelay: Boolean = false) {
        fetchJob?.cancel()
        val currentState = _uiState.value
        val currentQuery =
            (currentState as? NewsUiState.Success)?.articleQuery ?: ArticleQueryDisplayModel()
        val currentExpandedCards =
            (currentState as? NewsUiState.Success)?.expandedCards ?: emptySet()
        fetchJob = viewModelScope.launch {
            try {
                if (withDelay) delay(500)
                val domainQuery = mapper.mapToArticleQueryDomainModel(currentQuery)
                val articles = getTopHeadlinesUseCase(domainQuery)
                val displayArticles = mapper.mapToArticleDisplayModel(articles)

                _uiState.update {
                    NewsUiState.Success(
                        articles = displayArticles,
                        articleQuery = currentQuery,
                        expandedCards = currentExpandedCards,
                        isRefreshing = false
                    )
                }
            } catch (e: Exception) {
                if (e is CancellationException) return@launch
                val errorType = when (e) {
                    is DomainException.ServerException -> ErrorType.SERVER
                    is DomainException.ParseException -> ErrorType.PARSING
                    is DomainException.NetworkException -> ErrorType.NETWORK
                    is DomainException.TimeoutException -> ErrorType.NETWORK
                    else -> ErrorType.GENERIC
                }
                _uiState.update {
                    NewsUiState.Error(message = e.message ?: "Unknown error", errorType = errorType)
                }
            }
        }
    }
}