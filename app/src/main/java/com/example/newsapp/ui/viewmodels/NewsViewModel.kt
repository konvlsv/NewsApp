package com.example.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.preview.getMockArticleUiList
import com.example.newsapp.ui.state.NewsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel() : ViewModel() {

    private val _uiState: MutableStateFlow<NewsUiState> = MutableStateFlow(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        imitateDownload()
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

    fun onArticleSelectedCategoryChange(category: ArticleCategory) {
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
        imitateDownload()
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
            _uiState.update { state ->
                (state as? NewsUiState.Success)?.copy(isRefreshing = true) ?: state
            }
            try {
                delay(2000)
                val newArticles = getMockArticleUiList()
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
    private fun imitateDownload() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            delay(2000)
            try {
                val mockArticles = getMockArticleUiList()
                val previousSuccess = _uiState.value as? NewsUiState.Success

                _uiState.value = NewsUiState.Success(
                    articles = mockArticles,
                    searchQuery = previousSuccess?.searchQuery ?: "",
                    selectedCategory = previousSuccess?.selectedCategory ?: ArticleCategory.GENERAL,
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