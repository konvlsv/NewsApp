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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.cancellation.CancellationException

class DetailsViewModel(
    getArticleDetailsUseCase: GetArticleDetailsUseCase = App.instance.getArticleDetailsUseCase,
    private val mapper: UiModelsMapper = App.instance.uiModelsMapper,
    private val openUrlUseCase: OpenUrlUseCase = App.instance.openUrlUseCase,
    private val shareArticleUseCase: ShareArticleUseCase = App.instance.shareArticleUseCase,
) : ViewModel() {

    val state: StateFlow<DetailsState> = getArticleDetailsUseCase
        .getArticleDetails()
        .map { article ->
            val articleUi = mapper.toArticleUi(article)
            DetailsState(
                article = articleUi,
                isLoading = false,
                isRefreshing = false,
                errorState = null,
            )
        }.catch { throwable ->
            if (throwable is CancellationException) throw throwable
            val message = throwable.message ?: "Failed to load article"
            val errorType = when (throwable) {
                is DomainException.ServerException -> ErrorType.SERVER
                is DomainException.ParseException -> ErrorType.PARSING
                is DomainException.NetworkException -> ErrorType.NETWORK
                is DomainException.TimeoutException -> ErrorType.NETWORK
                else -> ErrorType.GENERIC
            }
            val errorState = ErrorState(message = message, errorType = errorType)
            emit(
                DetailsState(
                    article = ArticleUi.EMPTY,
                    isLoading = false,
                    isRefreshing = false,
                    errorState = errorState,
                )
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailsState(
                article = ArticleUi.EMPTY,
                isLoading = true,
                errorState = null,
            )
        )

    fun handleEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.OnRefresh -> onRefresh()
            is DetailsEvent.OnOpenInBrowser -> openInBrowser(event.article)
            is DetailsEvent.OnShare -> shareArticle(event.article)
        }
    }

    private fun onRefresh() {

    }

    private fun openInBrowser(article: ArticleUi) {
        openUrlUseCase.open(article.articleUrl)
    }

    private fun shareArticle(article: ArticleUi) {
        shareArticleUseCase.share(article.title, article.description, article.articleUrl)
    }
}