package com.example.newsapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsViewModel() : ViewModel() {

    var articleList by mutableStateOf(getMockArticleUiList())
        private set

    var articleSearchBarSearchQuery by mutableStateOf("")
        private set

    var articleSelectedCategory by mutableStateOf(ArticleCategory.GENERAL)
        private set

    var expandedCardIds = mutableStateSetOf<ArticleUi>()
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    private fun imitateDownload(){
        viewModelScope.launch {
            isRefreshing = true
            delay(2000)
            articleList = getMockArticleUiList()
            isRefreshing = false
        }
    }

    fun onArticleSelectedCategoryChange(category: ArticleCategory) {
        articleSelectedCategory = category
    }

    fun onArticleSearchBarValueChange(query: String) {
        articleSearchBarSearchQuery = query
    }

    fun onArticleSearchBarDeleteClick() {
        articleSearchBarSearchQuery = ""
    }

    fun onArticleSearchBarSearchClick() {
        imitateDownload()
    }

    fun isCardExpanded(article: ArticleUi): Boolean {
        return expandedCardIds.contains(article)
    }

    fun onExpandOrCollapseCardClick(article: ArticleUi) {
        if (expandedCardIds.contains(article)) {
            expandedCardIds.remove(article)
        } else {
            expandedCardIds.add(article)
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