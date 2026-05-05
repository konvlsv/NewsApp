package com.example.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.App
import com.example.newsapp.domain.usecase.GetCategoryTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.SearchTopHeadlinesUseCase
import com.example.newsapp.ui.models.ArticleCategoryDisplayModel
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.state.NewsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    val getCategoryTopHeadlinesUseCase: GetCategoryTopHeadlinesUseCase = App.instance.getCategoryTopHeadlinesUseCase,
    val searchTopHeadlinesUseCase: SearchTopHeadlinesUseCase = App.instance.searchTopHeadlinesUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<NewsUiState> = MutableStateFlow(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        loadArticles()
    }

    fun setDetailsArticle(article: ArticleDisplayModel) {
        _uiState.update { currentState ->
            when (currentState) {
                is NewsUiState.Success -> currentState.copy(detailsArticle = article)
                is NewsUiState.Loading -> currentState
                is NewsUiState.Error -> currentState
            }
        }
    }

    fun onArticleSelectedCategoryChange(category: ArticleCategoryDisplayModel) {
        _uiState.update { currentState ->
            when (currentState) {
                is NewsUiState.Success -> currentState.copy(selectedCategory = category)
                is NewsUiState.Loading -> currentState
                is NewsUiState.Error -> currentState
            }
        }
    }

    fun onArticleSearchBarValueChange(query: String) {
        _uiState.update { currentState ->
            when (currentState) {
                is NewsUiState.Success -> currentState.copy(searchQuery = query)
                is NewsUiState.Loading -> currentState
                is NewsUiState.Error -> currentState
            }
        }
    }

    fun onArticleSearchBarDeleteClick() {
        _uiState.update { currentState ->
            when (currentState) {
                is NewsUiState.Success -> currentState.copy(searchQuery = "")
                is NewsUiState.Loading -> currentState
                is NewsUiState.Error -> currentState
            }
        }
    }

    fun onArticleSearchBarSearchClick() {
        onRefresh()
    }

    fun onExpandOrCollapseCardClick(article: ArticleDisplayModel) {
        _uiState.update { currentState ->
            when (currentState) {
                is NewsUiState.Loading -> currentState
                is NewsUiState.Error -> currentState
                is NewsUiState.Success -> {
                    val newCards = currentState.expandedCards.toMutableSet()
                    if (newCards.contains(article)) {
                        newCards.remove(article)
                    } else {
                        newCards.add(article)
                    }
                    currentState.copy(expandedCards = newCards)
                }
            }
        }
    }

    fun onShareClick(article: ArticleDisplayModel) {

    }

    fun onOpenInBrowserClick(article: ArticleDisplayModel) {

    }

    fun onRefresh() {
        viewModelScope.launch {
            try {
                _uiState.update { state ->
                    (state as? NewsUiState.Success)?.copy(isRefreshing = true) ?: state
                }
                val previousSuccess = _uiState.value as? NewsUiState.Success
                val newArticles = if (previousSuccess?.searchQuery?.isEmpty() == true) {
                    getCategoryTopHeadlinesUseCase(ArticleCategoryDisplayModel.GENERAL.name)
                } else {
                    searchTopHeadlinesUseCase(
                        query = previousSuccess?.searchQuery ?: "",
                        category = previousSuccess?.selectedCategory?.name
                            ?: ArticleCategoryDisplayModel.GENERAL.name
                    )
                }
                _uiState.update { state ->
                    if (state is NewsUiState.Success) {
                        state.copy(
                            articles = newArticles,
                            isRefreshing = false
                        )
                    } else {
                        state
                    }
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    (state as? NewsUiState.Success)?.copy(isRefreshing = false) ?: state
                }
            }
        }
    }

    private fun loadArticles() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            try {
                val newArticles = getCategoryTopHeadlinesUseCase(ArticleCategoryDisplayModel.GENERAL.name)
                val previousSuccess = _uiState.value as? NewsUiState.Success

                _uiState.value = NewsUiState.Success(
                    articles = newArticles,
                    searchQuery = previousSuccess?.searchQuery ?: "",
                    selectedCategory = previousSuccess?.selectedCategory ?: ArticleCategoryDisplayModel.GENERAL,
                    expandedCards = previousSuccess?.expandedCards ?: emptySet(),
                    detailsArticle = previousSuccess?.detailsArticle
                )
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(
                    message = e.message ?: "Unknown error"
                )
            }
        }
    }
}