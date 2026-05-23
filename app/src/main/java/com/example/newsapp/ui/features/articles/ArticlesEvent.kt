package com.example.newsapp.ui.features.articles

import com.example.newsapp.ui.features.articles.models.ArticleCategoryDisplayModel
import com.example.newsapp.ui.common.models.ArticleDisplayModel

sealed interface ArticlesEvent {
    data object OnRefresh : ArticlesEvent
    data object OnArticleSearchBarDeleteClick : ArticlesEvent
    data object OnArticleSearchBarSearchClick : ArticlesEvent
    data class OnArticleSearchBarValueChange(val query: String) : ArticlesEvent
    data class OnArticleSelectedCategoryChange(val category: ArticleCategoryDisplayModel) : ArticlesEvent
    data class OnNavigateToArticleDetails(val article: ArticleDisplayModel) : ArticlesEvent
    data class OnShareClick(val article: ArticleDisplayModel) : ArticlesEvent
    data class OnExpandOrCollapseCardClick(val article: ArticleDisplayModel) : ArticlesEvent
    data class OpenInBrowserClick(val article: ArticleDisplayModel) : ArticlesEvent
}