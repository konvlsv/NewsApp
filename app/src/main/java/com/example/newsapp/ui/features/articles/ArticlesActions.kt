package com.example.newsapp.ui.features.articles

import com.example.newsapp.ui.features.articles.models.ArticleCategoryDisplayModel
import com.example.newsapp.ui.common.models.ArticleDisplayModel

sealed interface ArticlesActions {
    data object OnRefresh : ArticlesActions
    data object OnArticleSearchBarDeleteClick : ArticlesActions
    data object OnArticleSearchBarSearchClick : ArticlesActions
    data class OnArticleSearchBarValueChange(val query: String) : ArticlesActions
    data class OnArticleSelectedCategoryChange(val category: ArticleCategoryDisplayModel) : ArticlesActions
    data class OnNavigateToArticleDetails(val article: ArticleDisplayModel) : ArticlesActions
    data class OnShareClick(val article: ArticleDisplayModel) : ArticlesActions
    data class OnExpandOrCollapseCardClick(val article: ArticleDisplayModel) : ArticlesActions
    data class OpenInBrowserClick(val article: ArticleDisplayModel) : ArticlesActions
}