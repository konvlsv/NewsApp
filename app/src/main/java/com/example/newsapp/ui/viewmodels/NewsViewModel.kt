package com.example.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        imitateDownload()
    }

    private fun imitateDownload() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            delay(2000)
            val mockArticles = getMockArticleUiList()
            _uiState.update {
                it.copy(
                    articles = mockArticles,
                    isRefreshing = false
                )
            }
        }
    }

    fun onArticleSelectedCategoryChange(category: ArticleCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun onArticleSearchBarValueChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onArticleSearchBarDeleteClick() {
        _uiState.update { it.copy(searchQuery = "") }
    }

    fun onArticleSearchBarSearchClick() {
        imitateDownload()
    }

    fun onExpandOrCollapseCardClick(article: ArticleUi) {
        _uiState.update { currentState ->
            val newCards = currentState.expandedCards.toMutableSet()
            if (newCards.contains(article)) {
                newCards.remove(article)
            } else {
                newCards.add(article)
            }
            currentState.copy(expandedCards = newCards)
        }
    }

    fun onRefresh() {
        imitateDownload()
    }

    fun onShareClick(article: ArticleUi) {

    }

    fun onOpenInBrowserClick(article: ArticleUi) {

    }
}

data class NewsUiState(
    val articles: List<ArticleUi> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: ArticleCategory = ArticleCategory.GENERAL,
    val expandedCards: Set<ArticleUi> = emptySet(),
    val isRefreshing: Boolean = false,
    val isError: Boolean = false
)