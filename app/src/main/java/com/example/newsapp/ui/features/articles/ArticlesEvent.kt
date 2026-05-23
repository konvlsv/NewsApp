package com.example.newsapp.ui.features.articles

import com.example.newsapp.ui.features.articles.models.ArticleCategoryUi
import com.example.newsapp.ui.common.models.ArticleUi

sealed interface ArticlesEvent {
    data object OnRefresh : ArticlesEvent
    data object OnArticleSearchBarDeleteClick : ArticlesEvent
    data object OnArticleSearchBarSearchClick : ArticlesEvent
    data class OnArticleSearchBarValueChange(val query: String) : ArticlesEvent
    data class OnArticleSelectedCategoryChange(val category: ArticleCategoryUi) : ArticlesEvent
    data class OnNavigateToArticleDetails(val article: ArticleUi) : ArticlesEvent
    data class OnShareClick(val article: ArticleUi) : ArticlesEvent
    data class OnToggleArticleExpand(val article: ArticleUi) : ArticlesEvent
    data class OpenInBrowserClick(val article: ArticleUi) : ArticlesEvent
}