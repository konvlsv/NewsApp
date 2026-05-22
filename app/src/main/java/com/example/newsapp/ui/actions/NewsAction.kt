package com.example.newsapp.ui.actions

import com.example.newsapp.ui.models.ArticleCategoryDisplayModel
import com.example.newsapp.ui.models.ArticleDisplayModel

sealed interface NewsAction {
    data object OnRefresh : NewsAction
    data object OnArticleSearchBarDeleteClick : NewsAction
    data object OnArticleSearchBarSearchClick : NewsAction
    data class OnArticleSearchBarValueChange(val query: String) : NewsAction
    data class OnArticleSelectedCategoryChange(val category: ArticleCategoryDisplayModel) : NewsAction
    data class OnNavigateToArticleDetails(val article: ArticleDisplayModel) : NewsAction
    data class OnShareClick(val article: ArticleDisplayModel) : NewsAction
    data class OnExpandOrCollapseCardClick(val article: ArticleDisplayModel) : NewsAction
    data class OpenInBrowserClick(val article: ArticleDisplayModel) : NewsAction
}