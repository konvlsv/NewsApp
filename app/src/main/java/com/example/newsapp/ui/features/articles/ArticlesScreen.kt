package com.example.newsapp.ui.features.articles

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.features.articles.component.ArticleCard
import com.example.newsapp.ui.features.articles.component.ArticleSearchBar
import com.example.newsapp.ui.features.articles.component.ArticlesCategoryLazyRow
import com.example.newsapp.ui.common.preview.getMockSuccessNewsUiState
import com.example.newsapp.ui.state.UiState
import com.example.newsapp.ui.common.theme.AppTheme
import com.example.newsapp.ui.common.theme.NewsAppTheme
import com.example.newsapp.ui.common.components.ErrorScreen
import com.example.newsapp.ui.common.components.LoadingScreen

@Composable
fun ArticlesScreen(
    modifier: Modifier = Modifier,
    viewModel: ArticlesViewModel = viewModel(),
    onNavigateToArticleDetails: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.navigationEvent) {
        viewModel.navigationEvent.collect { target ->
            when (target) {
                is ArticlesNavigationTarget.Details -> onNavigateToArticleDetails()
            }
        }
    }

    when (val currentState = uiState) {
        is UiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is UiState.Error -> {
            ErrorScreen(message = currentState.message, modifier = modifier)
        }

        is UiState.Success -> {
            ArticlesContent(
                state = currentState.data,
                modifier = modifier,
                actions = viewModel::onAction
            )
        }
    }
}

@Composable
fun ArticlesContent(
    state: ArticlesState,
    actions: (ArticlesActions) -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { actions(ArticlesActions.OnRefresh) },
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            item(key = "search_bar") {
                ArticleSearchBar(
                    articleSearchBarSearchQuery = state.articleQuery.query,
                    onArticleSearchBarValueChange = { query ->
                        actions(ArticlesActions.OnArticleSearchBarValueChange(query))
                    },
                    onArticleSearchBarDeleteClick = { actions(ArticlesActions.OnArticleSearchBarDeleteClick) },
                    onArticleSearchBarSearchClick = { actions(ArticlesActions.OnArticleSearchBarSearchClick) },
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.paddingLarge)
                        .padding(vertical = AppTheme.dimens.paddingLarge)
                )
            }

            item(key = "categories") {
                ArticlesCategoryLazyRow(
                    articleSelectedCategory = state.articleQuery.category,
                    onArticleSelectedCategoryChange = { category ->
                        actions(ArticlesActions.OnArticleSelectedCategoryChange(category))
                    }
                )
            }

            items(items = state.articles, key = { it.url }) { article ->
                ArticleCard(
                    article = article,
                    onNavigateToArticleDetails = { actions(ArticlesActions.OnNavigateToArticleDetails(article)) },
                    onShareClick = { actions(ArticlesActions.OnShareClick(article)) },
                    onExpandOrCollapseCardClick = { actions(ArticlesActions.OnExpandOrCollapseCardClick(article)) },
                    openInBrowserClick = { actions(ArticlesActions.OpenInBrowserClick(article)) },
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
fun ArticlesContentPreview() {
    NewsAppTheme() {
        ArticlesContent(
            state = getMockSuccessNewsUiState().data,
            actions = {},
        )
    }
}

