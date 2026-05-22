package com.example.newsapp.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.components.ArticleCard
import com.example.newsapp.ui.components.ArticleSearchBar
import com.example.newsapp.ui.components.ArticlesCategoryLazyRow
import com.example.newsapp.ui.models.ArticleCategoryDisplayModel
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.preview.getMockSuccessNewsUiState
import com.example.newsapp.ui.state.NewsState
import com.example.newsapp.ui.state.UiState
import com.example.newsapp.ui.theme.AppTheme
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.viewmodels.NewsViewModel

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(),
    onNavigateToArticleDetails: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (state) {
        is UiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is UiState.Error -> {
            ErrorScreen(message = (state as UiState.Error).message, modifier = modifier)
        }

        is UiState.Success -> {
            NewsListContent(
                state = (state as UiState.Success).data,
                onNavigateToArticleDetails = { article ->
                    viewModel.saveDetailArticle(article)
                    onNavigateToArticleDetails()
                },
                onArticleSelectedCategoryChange = { viewModel.onArticleSelectedCategoryChange(it) },
                onExpandOrCollapseCardClick = { viewModel.onExpandOrCollapseCardClick(it) },
                onShareClick = { viewModel.shareArticle(it.title, it.description, it.url) },
                openInBrowserClick = { viewModel.openInBrowser(it.url) },
                onRefresh = { viewModel.onRefresh() },
                onArticleSearchBarValueChange = { viewModel.onArticleSearchBarValueChange(it) },
                onArticleSearchBarDeleteClick = { viewModel.onArticleSearchBarDeleteClick() },
                onArticleSearchBarSearchClick = { viewModel.onArticleSearchBarSearchClick() },
                modifier = modifier
            )
        }
    }
}

@Composable
fun NewsListContent(
    state: NewsState,
    onNavigateToArticleDetails: (ArticleDisplayModel) -> Unit,
    onArticleSelectedCategoryChange: (ArticleCategoryDisplayModel) -> Unit,
    onExpandOrCollapseCardClick: (ArticleDisplayModel) -> Unit,
    onShareClick: (ArticleDisplayModel) -> Unit,
    openInBrowserClick: (ArticleDisplayModel) -> Unit,
    onRefresh: () -> Unit,
    onArticleSearchBarValueChange: (String) -> Unit,
    onArticleSearchBarDeleteClick: () -> Unit,
    onArticleSearchBarSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item(key = "search_bar") {
                ArticleSearchBar(
                    articleSearchBarSearchQuery = state.articleQuery.query,
                    onArticleSearchBarValueChange = onArticleSearchBarValueChange,
                    onArticleSearchBarDeleteClick = onArticleSearchBarDeleteClick,
                    onArticleSearchBarSearchClick = onArticleSearchBarSearchClick,
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.paddingLarge)
                        .padding(vertical = AppTheme.dimens.paddingLarge)
                )
            }
            item(key = "categories") {
                ArticlesCategoryLazyRow(
                    articleSelectedCategory = state.articleQuery.category,
                    onArticleSelectedCategoryChange = onArticleSelectedCategoryChange
                )
            }
            items(
                items = state.articles,
                key = { it.url }
            ) { article ->
                ArticleCard(
                    onNavigateToArticleDetails = onNavigateToArticleDetails,
                    article = article,
                    onShareClick = onShareClick,
                    onExpandOrCollapseCardClick = onExpandOrCollapseCardClick,
                    isCardExpanded = { state.expandedCards.contains(it) },
                    openInBrowserClick = openInBrowserClick
                )
            }
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
fun NewsListContentPreview() {
    NewsAppTheme() {
        NewsListContent(
            state = getMockSuccessNewsUiState().data,
            onNavigateToArticleDetails = {},
            onArticleSelectedCategoryChange = {},
            onExpandOrCollapseCardClick = {},
            onShareClick = {},
            openInBrowserClick = {},
            onRefresh = {},
            onArticleSearchBarValueChange = {},
            onArticleSearchBarDeleteClick = {},
            onArticleSearchBarSearchClick = {}
        )
    }
}

