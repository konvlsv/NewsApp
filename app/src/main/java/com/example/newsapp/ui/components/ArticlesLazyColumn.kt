package com.example.newsapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.events.NewsListEvents
import com.example.newsapp.ui.events.getMockNewsListEvents
import com.example.newsapp.ui.theme.AppTheme
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.state.NewsUiState
import com.example.newsapp.ui.state.getMockSuccessNewsUiState

@Composable
fun ArticlesLazyColumn(
    state: NewsUiState.Success,
    events: NewsListEvents,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            ArticleSearchBar(
                articleSearchBarSearchQuery = state.searchQuery,
                onArticleSearchBarValueChange = events.onArticleSearchBarValueChange,
                onArticleSearchBarDeleteClick = events.onArticleSearchBarDeleteClick,
                onArticleSearchBarSearchClick = events.onArticleSearchBarSearchClick,
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimens.paddingLarge)
                    .padding(vertical = AppTheme.dimens.paddingLarge)
            )
            ArticlesCategoryLazyRow(
                articleSelectedCategory = state.selectedCategory,
                onArticleSelectedCategoryChange = events.onArticleSelectedCategoryChange
            )
        }
        items(
            items = state.articles,
            key = { it.id }
        ) { article ->
            ArticleCard(
                onNavigateToArticleDetails = events.onNavigateToArticleDetails,
                article = article,
                onShareClick = events.onShareClick,
                onExpandOrCollapseCardClick = events.onExpandOrCollapseCardClick,
                isCardExpanded = { state.expandedCards.contains(it) },
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    backgroundColor = 0xFFFFF9EE
)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    backgroundColor = 0xFF15130B
)
@Composable
fun ArticlesLazyColumnPreview() {
    NewsAppTheme() {
        ArticlesLazyColumn(
            state = getMockSuccessNewsUiState(),
            events = getMockNewsListEvents()
        )
    }
}