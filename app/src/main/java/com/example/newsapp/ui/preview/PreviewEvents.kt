package com.example.newsapp.ui.preview

import com.example.newsapp.ui.events.NewsListEvents

fun getMockNewsListEvents(): NewsListEvents {
    return NewsListEvents(
        onNavigateToArticleDetails = {},
        onArticleSelectedCategoryChange = {},
        onExpandOrCollapseCardClick = {},
        onShareClick = {},
        openInBrowserClick = {},
        onRefresh = {},
        onArticleSearchBarValueChange = {},
        onArticleSearchBarDeleteClick = {},
        onArticleSearchBarSearchClick = {},
    )
}