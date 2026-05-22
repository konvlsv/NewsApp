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
import com.example.newsapp.ui.actions.NewsAction
import com.example.newsapp.ui.components.ArticleCard
import com.example.newsapp.ui.components.ArticleSearchBar
import com.example.newsapp.ui.components.ArticlesCategoryLazyRow
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val currentState = uiState) {
        is UiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is UiState.Error -> {
            ErrorScreen(message = currentState.message, modifier = modifier)
        }

        is UiState.Success -> {
            NewsListContent(
                state = currentState.data,
                modifier = modifier,
                actions = { action ->
                    viewModel.onAction(action)
                    if (action is NewsAction.OnNavigateToArticleDetails) {
                        onNavigateToArticleDetails()
                    }
                }
            )
        }
    }
}

@Composable
fun NewsListContent(
    state: NewsState,
    actions: (NewsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { actions(NewsAction.OnRefresh) },
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            item(key = "search_bar") {
                ArticleSearchBar(
                    articleSearchBarSearchQuery = state.articleQuery.query,
                    onArticleSearchBarValueChange = { query ->
                        actions(NewsAction.OnArticleSearchBarValueChange(query))
                    },
                    onArticleSearchBarDeleteClick = { actions(NewsAction.OnArticleSearchBarDeleteClick) },
                    onArticleSearchBarSearchClick = { actions(NewsAction.OnArticleSearchBarSearchClick) },
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.paddingLarge)
                        .padding(vertical = AppTheme.dimens.paddingLarge)
                )
            }

            item(key = "categories") {
                ArticlesCategoryLazyRow(
                    articleSelectedCategory = state.articleQuery.category,
                    onArticleSelectedCategoryChange = { category ->
                        actions(NewsAction.OnArticleSelectedCategoryChange(category))
                    }
                )
            }

            items(items = state.articles, key = { it.url }) { article ->
                ArticleCard(
                    article = article,
                    onNavigateToArticleDetails = { actions(NewsAction.OnNavigateToArticleDetails(article)) },
                    onShareClick = { actions(NewsAction.OnShareClick(article)) },
                    onExpandOrCollapseCardClick = { actions(NewsAction.OnExpandOrCollapseCardClick(article)) },
                    openInBrowserClick = { actions(NewsAction.OpenInBrowserClick(article)) },
                    isCardExpanded = { state.expandedCards.contains(article) }
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
            actions = {},
        )
    }
}

