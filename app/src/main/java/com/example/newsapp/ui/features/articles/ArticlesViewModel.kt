package com.example.newsapp.ui.features.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.App
import com.example.newsapp.domain.exception.DomainException
import com.example.newsapp.domain.navigation.BrowserNavigator
import com.example.newsapp.domain.share.ShareManager
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.SaveDetailArticleUseCase
import com.example.newsapp.ui.common.mapper.DisplayModelsMapper
import com.example.newsapp.ui.common.models.ArticleDisplayModel
import com.example.newsapp.ui.features.articles.models.ArticleCategoryDisplayModel
import com.example.newsapp.ui.features.articles.models.ArticleQueryDisplayModel
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

sealed interface ArticlesNavigationTarget {
    object Details : ArticlesNavigationTarget
}

class ArticlesViewModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase = App.instance.getTopHeadlinesUseCase,
    private val mapper: DisplayModelsMapper = App.instance.displayModelsMapper,
    private val saveDetailArticleUseCase: SaveDetailArticleUseCase = App.instance.saveDetailArticleUseCase,
    private val shareManager: ShareManager = App.instance.shareManager,
    private val browserNavigator: BrowserNavigator = App.instance.browserNavigator
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

    fun onAction(action: ArticlesActions) {
        when (action) {
            is ArticlesActions.OnRefresh -> onRefresh()
            is ArticlesActions.OnArticleSearchBarDeleteClick -> onArticleSearchBarDeleteClick()
            is ArticlesActions.OnArticleSearchBarSearchClick -> onArticleSearchBarSearchClick()
            is ArticlesActions.OnArticleSearchBarValueChange -> onArticleSearchBarValueChange(action.query)
            is ArticlesActions.OnShareClick -> shareArticle(action.article)
            is ArticlesActions.OnExpandOrCollapseCardClick -> onExpandOrCollapseCardClick(action.article)
            is ArticlesActions.OpenInBrowserClick -> openInBrowser(action.article)
            is ArticlesActions.OnNavigateToArticleDetails -> onNavigateToArticleDetails(action.article)
            is ArticlesActions.OnArticleSelectedCategoryChange -> onArticleSelectedCategoryChange(
                action.category
            )
        }
    }

    private fun onNavigateToArticleDetails(article: ArticleDisplayModel) {
        viewModelScope.launch {
            try {
                saveDetailArticle(article)
                _navigationEvent.send(ArticlesNavigationTarget.Details)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun saveDetailArticle(article: ArticleDisplayModel) {
        val domainArticle = mapper.mapToArticleDomainModel(article) //
        saveDetailArticleUseCase(domainArticle)
    }

    private fun shareArticle(article: ArticleDisplayModel) {
        shareManager.shareArticle(article.title, article.description, article.url)
    }

    private fun openInBrowser(article: ArticleDisplayModel) {
        browserNavigator.openUrl(article.url)
    }


    private fun onArticleSelectedCategoryChange(category: ArticleCategoryDisplayModel) {
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

    private fun onExpandOrCollapseCardClick(article: ArticleDisplayModel) {
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