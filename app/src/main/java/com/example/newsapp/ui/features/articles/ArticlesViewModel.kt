package com.example.newsapp.ui.features.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.App
import com.example.newsapp.domain.exception.DomainException
import com.example.newsapp.domain.models.ArticleQuery
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.OpenUrlUseCase
import com.example.newsapp.domain.usecase.SaveDetailsArticleUseCase
import com.example.newsapp.domain.usecase.ShareArticleUseCase
import com.example.newsapp.ui.common.mapper.UiModelsMapper
import com.example.newsapp.ui.common.models.ArticleUi
import com.example.newsapp.ui.features.articles.models.ArticleCategoryUi
import com.example.newsapp.ui.state.ErrorState
import com.example.newsapp.ui.state.ErrorType
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
    private val saveDetailsArticleUseCase: SaveDetailsArticleUseCase = App.instance.saveDetailsArticleUseCase,
    private val openUrlUseCase: OpenUrlUseCase = App.instance.openUrlUseCase,
    private val shareArticleUseCase: ShareArticleUseCase = App.instance.shareArticleUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ArticlesState())
    val state: StateFlow<ArticlesState> = _state.asStateFlow()

    private val _navigationEvent = Channel<ArticlesNavigationTarget>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    init {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
                errorState = null,
                isRefreshing = false,
                articles = emptyList(),
                expandedArticleUrls = emptySet(),
                searchQuery = "",
                selectedCategory = ArticleCategoryUi.GENERAL,
            )
        }
        executeSearchRequest(message = "Failed to load articles")
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
        executeSearchRequest(message = "Failed to load articles")
    }

    private fun onClear() {
        _state.update { it.copy(searchQuery = "") }
        executeSearchRequest(message = "Failed to load articles")
    }

    private fun onSearch() {
        executeSearchRequest(message = "Failed to load articles")
    }

    private fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
        executeSearchRequest(message = "Failed to load articles", withDelay = true)
    }

    private fun onCategorySelected(category: ArticleCategoryUi) {
        _state.update { it.copy(selectedCategory = category) }
        executeSearchRequest(message = "Failed to load articles")
    }

    private fun onShare(article: ArticleUi) {
        shareArticleUseCase.share(article.title, article.description, article.articleUrl)
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
        openUrlUseCase.open(article.articleUrl)
    }

    private fun onNavigateToDetails(article: ArticleUi) {
        viewModelScope.launch {
            runCatching {
                saveDetailsArticleUseCase(mapper.toArticle(article))
            }.onSuccess {
                _navigationEvent.send(ArticlesNavigationTarget.TargetToDetails)
            }.onFailure { throwable ->
                if (throwable is CancellationException) return@launch
                handleException(throwable, "Failed navigate")
            }
        }
    }

    private fun executeSearchRequest(message: String, withDelay: Boolean = false) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (withDelay) delay(500)
            _state.update { currentState ->
                currentState.copy(
                    errorState = null,
                    isRefreshing = !currentState.isLoading
                )
            }
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
                isLoading = false,
                isRefreshing = false,
            )
        }
    }
}