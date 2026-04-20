package com.example.newsapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList

class NewsViewModel() : ViewModel() {

    var articleList by mutableStateOf(getMockArticleUiList())
        private set

    var articleSearchBarSearchQuery by mutableStateOf("")
        private set

    var articleSelectedCategory by mutableStateOf(ArticleCategory.GENERAL)
        private set

    var expandedCardIds = mutableStateSetOf<ArticleUi>()

    fun onArticleSelectedCategoryChange(category: ArticleCategory) {
        articleSelectedCategory = category
    }

    fun onArticleSearchBarValueChange(query: String) {
        articleSearchBarSearchQuery = query
    }

    fun onArticleSearchBarDeleteClick() {
        articleSearchBarSearchQuery = ""
    }

    fun onArticleSearchBarSearchClick(){
        //todo начать поиск
    }

    fun isCardExpanded(article: ArticleUi): Boolean {
         return expandedCardIds.contains(article)
    }

    fun onExpandOrCollapseCardClick(article: ArticleUi) {
        if (expandedCardIds.contains(article)) {
            expandedCardIds.remove(article)
        }else {
            expandedCardIds.add(article)
        }
    }

    fun isRefreshing(): Boolean {
        return false
    }

    fun onRefresh() {

    }

    fun onShareClick(article: ArticleUi) {

    }

    fun onOpenInBrowserClick(article: ArticleUi) {

    }
}