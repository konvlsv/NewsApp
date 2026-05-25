package com.example.newsapp.ui.features.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.App
import com.example.newsapp.domain.exception.DomainException
import com.example.newsapp.domain.models.ArticleQuery
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.OpenUrlUseCase
import com.example.newsapp.domain.usecase.SaveDetailArticleUseCase
import com.example.newsapp.domain.usecase.ShareArticleUseCase
import com.example.newsapp.ui.common.mapper.UiModelsMapper
import com.example.newsapp.ui.common.models.ArticleUi
import com.example.newsapp.ui.features.articles.models.ArticleCategoryUi
import com.example.newsapp.ui.state.ErrorState
import com.example.newsapp.ui.state.ErrorType
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
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

    private val _state = MutableStateFlow(ArticlesState())
    val state: StateFlow<ArticlesState> = _state.asStateFlow()

    private val _navigationEvent = Channel<ArticlesNavigationTarget>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private var viewModelJob: Job? = null

    init {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
                errorState = null,
                isError = false,
                isRefreshing = false,
                articles = emptyList(),
                expandedArticleUrls = emptySet(),
                searchQuery = "",
                selectedCategory = ArticleCategoryUi.GENERAL,
            )
        }
        handleLoading("Failed to load articles")
    }

    fun handleEvent(event: ArticlesEvent) {
        when (event) {
            is ArticlesEvent.OnRefresh -> onRefresh()
            is ArticlesEvent.OnClear -> onClear()
            is ArticlesEvent.OnSearch -> onSearch()
            is ArticlesEvent.OnSearchQueryChange -> onSearchQueryChange(event.query)
            is ArticlesEvent.OnCategorySelected -> onCategorySelected(event.category)
            is ArticlesEvent.OnShare -> onShare(event.article)
            is ArticlesEvent.OnToggleExpand -> onToggleExpand(event.article)
            is ArticlesEvent.OnOpenInBrowser -> onOpenInBrowser(event.article)
            is ArticlesEvent.OnNavigateToADetails -> onNavigateToDetails(event.article)
        }
    }

    private fun onRefresh() {
        _state.update { currentState ->
            currentState.copy(
                isLoading = false,
                errorState = null,
                isError = false,
                isRefreshing = true,
            )
        }
        handleLoading("Failed to load articles")
    }

    private fun onClear() {
        _state.update { currentState ->
            currentState.copy(
                isLoading = false,
                errorState = null,
                isError = false,
                isRefreshing = true,
                searchQuery = "",
            )
        }
        handleLoading("Failed to load articles")
    }

    private fun onSearch() {
        val query = _state.value.searchQuery.trim()
        if (query.isBlank()) return
        _state.update { currentState ->
            currentState.copy(
                isLoading = false,
                errorState = null,
                isError = false,
                isRefreshing = true,
            )
        }
        handleLoading("Failed to load articles")
    }

    private fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    private fun onCategorySelected(category: ArticleCategoryUi) {
        _state.update { currentState ->
            currentState.copy(
                isLoading = false,
                errorState = null,
                isError = false,
                isRefreshing = true,
                selectedCategory = category,
            )
        }
        handleLoading("Failed to load articles")
    }

    private fun onShare(article: ArticleUi) {
        shareArticleUseCase(article.title, article.description, article.articleUrl)
    }

    private fun onToggleExpand(article: ArticleUi) {
        _state.update { current ->
            val expandedUrls = current.expandedArticleUrls.toMutableSet()
            if (article.articleUrl in expandedUrls) {
                expandedUrls.remove(article.articleUrl)
            } else {
                expandedUrls.add(article.articleUrl)
            }
            current.copy(expandedArticleUrls = expandedUrls)
        }
    }

    private fun onOpenInBrowser(article: ArticleUi) {
        openUrlUseCase(article.articleUrl)
    }

    private fun onNavigateToDetails(article: ArticleUi) {
        viewModelJob?.cancel()
        viewModelJob = viewModelScope.launch {
            runCatching {
                saveDetailArticleUseCase(mapper.toArticle(article))
            }.onSuccess {
                _navigationEvent.send(ArticlesNavigationTarget.TargetToDetails)
            }.onFailure { throwable ->
                if (throwable is CancellationException) return@launch
                handleException(throwable, "Failed navigate")
            }
        }
    }

    private fun handleLoading(message: String) {
        viewModelJob?.cancel()
        viewModelJob = viewModelScope.launch {
            runCatching {
                getTopHeadlinesUseCase(getCurrentQuery())
            }.onSuccess { articles ->
                handleSuccess(mapper.toArticleUi(articles))
            }.onFailure { throwable ->
                if (throwable is CancellationException) return@launch
                handleException(throwable, message)
            }
        }
    }

    private fun getCurrentQuery(): ArticleQuery {
        return ArticleQuery(
            query = _state.value.searchQuery,
            category = mapper.toArticleCategory(_state.value.selectedCategory)
        )
    }

    private fun handleSuccess(articles: List<ArticleUi>) {
        _state.update { currentState ->
            currentState.copy(
                articles = articles,
                isLoading = false,
                isRefreshing = false,
                errorState = null,
                isError = false,
            )
        }
    }

    private fun handleException(throwable: Throwable, message: String) {
        val message = throwable.message ?: message
        val errorType = when (throwable) {
            is DomainException.ServerException -> ErrorType.SERVER
            is DomainException.ParseException -> ErrorType.PARSING
            is DomainException.NetworkException -> ErrorType.NETWORK
            is DomainException.TimeoutException -> ErrorType.NETWORK
            else -> ErrorType.GENERIC
        }
        val errorState = ErrorState(message = message, errorType = errorType)
        _state.update { currentState ->
            currentState.copy(
                errorState = errorState,
                isError = true,
                isLoading = false,
                isRefreshing = false,
            )
        }
    }
}