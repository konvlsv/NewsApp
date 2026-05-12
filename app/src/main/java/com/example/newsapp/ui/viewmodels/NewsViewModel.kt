package com.example.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.App
import com.example.newsapp.domain.exception.DomainException
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.SaveDetailArticleUseCase
import com.example.newsapp.ui.mapper.DisplayModelsMapper
import com.example.newsapp.ui.models.ArticleCategoryDisplayModel
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.models.ArticleQueryDisplayModel
import com.example.newsapp.ui.state.ErrorType
import com.example.newsapp.ui.state.NewsState
import com.example.newsapp.ui.state.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class NewsViewModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase = App.instance.getTopHeadlinesUseCase,
    private val mapper: DisplayModelsMapper = App.instance.displayModelsMapper,
    private val saveDetailArticleUseCase: SaveDetailArticleUseCase = App.instance.saveDetailArticleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<NewsState>>(UiState.Loading())
    val uiState: StateFlow<UiState<NewsState>> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        _uiState.update { UiState.Loading() }
        loadArticles()
    }

    fun saveDetailArticle(article: ArticleDisplayModel){
        viewModelScope.launch {
            try {
                val domainArticle = mapper.mapToArticleDomainModel(article)
                saveDetailArticleUseCase(domainArticle)
            }catch (e: Exception) {
                if (e is CancellationException) return@launch
            }
        }
    }

    fun onArticleSelectedCategoryChange(category: ArticleCategoryDisplayModel) {
        _uiState.update { currentState ->
            if (currentState is UiState.Success) {
                currentState.copy(
                    data = currentState.data.copy(
                        isRefreshing = true,
                        articleQuery = currentState.data.articleQuery.copy(category = category)
                    )
                )
            } else currentState
        }
        loadArticles(true)
    }

    fun onArticleSearchBarValueChange(query: String) {
        _uiState.update { currentState ->
            if (currentState is UiState.Success) {
                currentState.copy(
                    data = currentState.data.copy(
                        isRefreshing = true,
                        articleQuery = currentState.data.articleQuery.copy(query = query)
                    )
                )
            } else currentState
        }
        loadArticles(true)
    }

    fun onArticleSearchBarDeleteClick() {
        _uiState.update { currentState ->
            if (currentState is UiState.Success) {
                currentState.copy(
                    data = currentState.data.copy(
                        isRefreshing = true,
                        articleQuery = currentState.data.articleQuery.copy(query = "")
                    )
                )
            } else currentState
        }
        loadArticles(true)
    }

    fun onExpandOrCollapseCardClick(article: ArticleDisplayModel) {
        _uiState.update { currentState ->
            if (currentState is UiState.Success) {
                val newCards = currentState.data.expandedCards.toMutableSet()
                if (newCards.contains(article)) {
                    newCards.remove(article)
                } else {
                    newCards.add(article)
                }
                currentState.copy(
                    data = currentState.data.copy(expandedCards = newCards)
                )
            } else currentState
        }
    }

    fun onArticleSearchBarSearchClick() {
        onRefresh()
    }

    fun onRefresh() {
        _uiState.update { currentState ->
            if (currentState is UiState.Success) {
                currentState.copy(
                    data = currentState.data.copy(
                        isRefreshing = true,
                    )
                )
            } else currentState
        }
        loadArticles()
    }

    private fun loadArticles(withDelay: Boolean = false) {
        fetchJob?.cancel()
        val currentState = _uiState.value
        val currentQuery =
            (currentState as? UiState.Success)?.data?.articleQuery ?: ArticleQueryDisplayModel()
        val currentExpandedCards =
            (currentState as? UiState.Success)?.data?.expandedCards ?: emptySet()
        fetchJob = viewModelScope.launch {
            try {
                if (withDelay) delay(500)
                val domainQuery = mapper.mapToArticleQueryDomainModel(currentQuery)
                val articles = getTopHeadlinesUseCase(domainQuery)
                val displayArticles = mapper.mapToArticleDisplayModel(articles)

                _uiState.update {
                    UiState.Success(
                        data = NewsState(
                            articles = displayArticles,
                            articleQuery = currentQuery,
                            expandedCards = currentExpandedCards,
                            isRefreshing = false
                        )
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
                    UiState.Error(message = e.message ?: "Unknown error", errorType = errorType)
                }
            }
        }
    }
}