package com.example.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.App
import com.example.newsapp.domain.usecase.GetDetailArticleUseCase
import com.example.newsapp.ui.mapper.DisplayModelsMapper
import com.example.newsapp.ui.state.DetailsState
import com.example.newsapp.ui.state.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class ArticleDetailsViewModel(
    private val getDetailArticleUseCase: GetDetailArticleUseCase = App.instance.getDetailArticleUseCase,
    private val mapper: DisplayModelsMapper = App.instance.displayModelsMapper
): ViewModel() {

    private var fetchJob: Job? = null

    private val _uiState = MutableStateFlow<UiState<DetailsState>>(UiState.Loading())
    val uiState: StateFlow<UiState<DetailsState>> = _uiState.asStateFlow()

    init {
        _uiState.update { UiState.Loading() }
        loadArticle()
    }

    private fun loadArticle(){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val detailArticle = mapper.mapToArticleDisplayModel(getDetailArticleUseCase())
                _uiState.update {
                    UiState.Success(
                        data = DetailsState(
                            detail = detailArticle
                        )
                    )
                }
            }catch (e: Exception) {
                if (e is CancellationException) return@launch
                _uiState.update {
                    UiState.Error(message = e.message ?: "Unknown error")
                }
            }
        }
    }
}