package com.example.newsapp.ui.events

import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleDisplayModel

data class NewsListEvents(
    val onNavigateToArticleDetails: (ArticleDisplayModel) -> Unit,
    val onArticleSelectedCategoryChange: (ArticleCategory) -> Unit,
    val onExpandOrCollapseCardClick: (ArticleDisplayModel) -> Unit,
    val onShareClick: (ArticleDisplayModel) -> Unit,
    val onRefresh: () -> Unit,
    val onArticleSearchBarValueChange: (String) -> Unit,
    val onArticleSearchBarDeleteClick: () -> Unit,
    val onArticleSearchBarSearchClick: () -> Unit,
)