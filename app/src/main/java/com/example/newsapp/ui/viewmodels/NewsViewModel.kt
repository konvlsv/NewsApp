package com.example.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList

class NewsViewModel() : ViewModel() {


    fun getArticles(category: ArticleCategory = ArticleCategory.GENERAL): List<ArticleUi> {
        return getMockArticleUiList()
    }

    fun onShareClick(article: ArticleUi) {

    }

    fun onOpenInBrowserClick(article: ArticleUi) {

    }

    fun articleSelectedCategory(): ArticleCategory {
        return ArticleCategory.GENERAL
    }

    fun isCardExpanded(article: ArticleUi): Boolean {
        return false
    }

    fun isRefreshing(): Boolean {
        return false
    }

    fun onRefresh() {

    }

    fun onArticleSelectedCategoryChange(category: ArticleCategory) {

    }

    fun onExpandOrCollapseCardClick(article: ArticleUi) {

    }

    fun articleSearchBarSearchQuery(): String {
        return ""
    }

    fun onArticleSearchBarValueChange(query: String) {

    }

    fun onArticleSearchBarDeleteClick() {

    }

    fun onArticleSearchBarSearchClick(){

    }
}