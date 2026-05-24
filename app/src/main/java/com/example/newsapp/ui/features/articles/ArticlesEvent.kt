package com.example.newsapp.ui.features.articles

import com.example.newsapp.ui.features.articles.models.ArticleCategoryUi
import com.example.newsapp.ui.common.models.ArticleUi

sealed interface ArticlesEvent {
    data object OnRefresh : ArticlesEvent
    data object OnClear : ArticlesEvent
    data object OnSearch : ArticlesEvent
    data class OnSearchQueryChange(val query: String) : ArticlesEvent
    data class OnCategorySelected(val category: ArticleCategoryUi) : ArticlesEvent
    data class OnShare(val article: ArticleUi) : ArticlesEvent
    data class OnToggleExpand(val article: ArticleUi) : ArticlesEvent
    data class OnOpenInBrowser(val article: ArticleUi) : ArticlesEvent
    data class OnNavigateToADetails(val article: ArticleUi) : ArticlesEvent
}