package com.example.newsapp.ui.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.App
import com.example.newsapp.domain.exception.DomainException
import com.example.newsapp.domain.usecase.GetDetailArticleUseCase
import com.example.newsapp.domain.usecase.OpenUrlUseCase
import com.example.newsapp.domain.usecase.ShareArticleUseCase
import com.example.newsapp.ui.common.mapper.UiModelsMapper
import com.example.newsapp.ui.state.ErrorState
import com.example.newsapp.ui.state.ErrorType
import com.example.newsapp.ui.state.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class DetailsViewModel(
    private val getDetailArticleUseCase: GetDetailArticleUseCase = App.instance.getDetailArticleUseCase,
    private val mapper: UiModelsMapper = App.instance.uiModelsMapper,
    private val openUrlUseCase: OpenUrlUseCase = App.instance.openUrlUseCase,
    private val shareArticleUseCase: ShareArticleUseCase = App.instance.shareArticleUseCase,
) : ViewModel() {

    private var fetchJob: Job? = null

    private val _uiState = MutableStateFlow<UiState<DetailsState>>(UiState.Loading)
    val uiState: StateFlow<UiState<DetailsState>> = _uiState.asStateFlow()

    init {
        _uiState.update { UiState.Loading }
        loadArticle()
    }

    fun openInBrowser(url: String) {
        openUrlUseCase(url)
    }

    fun shareArticle(title: String, description: String, url: String) {
        shareArticleUseCase(title, description, url)
    }

    private fun loadArticle() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val detailArticle = mapper.toArticleUi(getDetailArticleUseCase())
                _uiState.update {
                    UiState.Success(
                        data = DetailsState(
                            detail = detailArticle
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