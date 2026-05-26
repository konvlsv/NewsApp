package com.example.newsapp.ui.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.App
import com.example.newsapp.domain.exception.DomainException
import com.example.newsapp.domain.usecase.GetArticleDetailsUseCase
import com.example.newsapp.domain.usecase.OpenUrlUseCase
import com.example.newsapp.domain.usecase.ShareArticleUseCase
import com.example.newsapp.ui.common.mapper.UiModelsMapper
import com.example.newsapp.ui.common.models.ArticleUi
import com.example.newsapp.ui.state.ErrorState
import com.example.newsapp.ui.state.ErrorType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class DetailsViewModel(
    private val getArticleDetailsUseCase: GetArticleDetailsUseCase = App.instance.getArticleDetailsUseCase,
    private val mapper: UiModelsMapper = App.instance.uiModelsMapper,
    private val openUrlUseCase: OpenUrlUseCase = App.instance.openUrlUseCase,
    private val shareArticleUseCase: ShareArticleUseCase = App.instance.shareArticleUseCase,
) : ViewModel() {

    private var fetchJob: Job? = null

    private val _state = MutableStateFlow<DetailsState>(DetailsState())
    val state: StateFlow<DetailsState> = _state.asStateFlow()

    init {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
                errorState = null,
                isRefreshing = false,
            )
        }
        loadArticle()
    }

    fun handleEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.OnRefresh -> onRefresh()
            is DetailsEvent.OnOpenInBrowser -> openInBrowser(event.article)
            is DetailsEvent.OnShare -> shareArticle(event.article)
        }
    }

    private fun onRefresh() {
        loadArticle()
    }

    private fun openInBrowser(article: ArticleUi) {
        openUrlUseCase.open(article.articleUrl)
    }

    private fun shareArticle(article: ArticleUi) {
        shareArticleUseCase.share(article.title, article.description, article.articleUrl)
    }

    private fun loadArticle() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    errorState = null,
                    isRefreshing = !currentState.isLoading
                )
            }
            runCatching {
                getArticleDetailsUseCase()
            }.onSuccess { article ->
                handleSuccess(mapper.toArticleUi(article))
            }.onFailure { throwable ->
                if (throwable is CancellationException) return@launch
                handleException(throwable, "Failed to load article")
            }
        }
    }

    private fun handleSuccess(article: ArticleUi) {
        _state.update { currentState ->
            currentState.copy(
                article = article,
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