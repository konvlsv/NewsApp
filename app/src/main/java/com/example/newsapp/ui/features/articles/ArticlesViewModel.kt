package com.example.newsapp.ui.features.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.App
import com.example.newsapp.domain.exception.DomainException
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.OpenUrlUseCase
import com.example.newsapp.domain.usecase.SaveDetailArticleUseCase
import com.example.newsapp.domain.usecase.ShareArticleUseCase
import com.example.newsapp.ui.common.mapper.UiModelsMapper
import com.example.newsapp.ui.common.models.ArticleUi
import com.example.newsapp.ui.features.articles.models.ArticleCategoryUi
import com.example.newsapp.ui.features.articles.models.ArticleQueryUi
import com.example.newsapp.ui.state.ErrorState
import com.example.newsapp.ui.state.ErrorType
import com.example.newsapp.ui.state.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class ArticlesViewModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase = App.instance.getTopHeadlinesUseCase,
    private val mapper: UiModelsMapper = App.instance.uiModelsMapper,
    private val saveDetailArticleUseCase: SaveDetailArticleUseCase = App.instance.saveDetailArticleUseCase,
    private val openUrlUseCase: OpenUrlUseCase = App.instance.openUrlUseCase,
    private val shareArticleUseCase: ShareArticleUseCase = App.instance.shareArticleUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ArticlesState>>(UiState.Loading)
    val uiState: StateFlow<UiState<ArticlesState>> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<ArticlesNavigationTarget>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    init {
        _uiState.update { UiState.Loading }
        loadArticles()
    }

    fun onAction(action: ArticlesEvent) {
        when (action) {
            is ArticlesEvent.OnRefresh -> onRefresh()
            is ArticlesEvent.OnArticleSearchBarDeleteClick -> onArticleSearchBarDeleteClick()
            is ArticlesEvent.OnArticleSearchBarSearchClick -> onArticleSearchBarSearchClick()
            is ArticlesEvent.OnArticleSearchBarValueChange -> onArticleSearchBarValueChange(action.query)
            is ArticlesEvent.OnShareClick -> shareArticle(action.article)
            is ArticlesEvent.OnExpandOrCollapseCardClick -> onExpandOrCollapseCardClick(action.article)
            is ArticlesEvent.OpenInBrowserClick -> openInBrowser(action.article)
            is ArticlesEvent.OnNavigateToArticleDetails -> onNavigateToArticleDetails(action.article)
            is ArticlesEvent.OnArticleSelectedCategoryChange -> onArticleSelectedCategoryChange(
                action.category
            )
        }
    }

    private fun onNavigateToArticleDetails(article: ArticleUi) {
        viewModelScope.launch {
            try {
                saveDetailArticle(article)
                _navigationEvent.send(ArticlesNavigationTarget.TargetToDetails)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun saveDetailArticle(article: ArticleUi) {
        val domainArticle = mapper.toArticle(article) //
        saveDetailArticleUseCase(domainArticle)
    }

    private fun shareArticle(article: ArticleUi) {
        shareArticleUseCase(article.title, article.description, article.url)
    }

    private fun openInBrowser(article: ArticleUi) {
        openUrlUseCase(article.url)
    }


    private fun onArticleSelectedCategoryChange(category: ArticleCategoryUi) {
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

    private fun onArticleSearchBarDeleteClick() {
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

    private fun onExpandOrCollapseCardClick(article: ArticleUi) {
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

    private fun onArticleSearchBarSearchClick() {
        onRefresh()
    }

    private fun onRefresh() {
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
            (currentState as? UiState.Success)?.data?.articleQuery ?: ArticleQueryUi()
        val currentExpandedCards =
            (currentState as? UiState.Success)?.data?.expandedCards ?: emptySet()
        fetchJob = viewModelScope.launch {
            try {
                if (withDelay) delay(500)
                val domainQuery = mapper.toArticleQuery(currentQuery)
                val articles = getTopHeadlinesUseCase(domainQuery)
                val displayArticles = mapper.toArticleUi(articles)

                _uiState.update {
                    UiState.Success(
                        data = ArticlesState(
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
                    UiState.Error(
                        data = ErrorState(
                            message = e.message ?: "Unknown error",
                            errorType = errorType
                        )
                    )
                }
            }
        }
    }
}