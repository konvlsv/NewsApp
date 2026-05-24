package com.example.newsapp.ui.features.articles

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.common.components.ErrorScreen
import com.example.newsapp.ui.common.components.LoadingScreen
import com.example.newsapp.ui.common.preview.getMockSuccessNewsUiState
import com.example.newsapp.ui.common.theme.AppTheme
import com.example.newsapp.ui.common.theme.NewsAppTheme
import com.example.newsapp.ui.features.articles.components.ArticleCard
import com.example.newsapp.ui.features.articles.components.ArticleSearchBar
import com.example.newsapp.ui.features.articles.components.ArticlesCategorySelector
import com.example.newsapp.ui.state.UiState

@Composable
fun ArticlesScreen(
    modifier: Modifier = Modifier,
    viewModel: ArticlesViewModel = viewModel(),
    onNavigateToArticleDetails: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel.navigationEvent) {
        viewModel.navigationEvent.collect { target ->
            when (target) {
                is ArticlesNavigationTarget.TargetToDetails -> onNavigateToArticleDetails()
            }
        }
    }

    when (val currentState = uiState) {
        is UiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is UiState.Error -> {
            ErrorScreen(message = currentState.data.message, modifier = modifier)
        }

        is UiState.Success -> {
            ArticlesContent(
                state = currentState.data,
                modifier = modifier,
                onEvent = viewModel::handleEvent
            )
        }
    }
}

@Composable
fun ArticlesContent(
    state: ArticlesState,
    onEvent: (ArticlesEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { onEvent(ArticlesEvent.OnRefresh) },
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.paddingMedium)
        ) {

            item(key = "search_bar") {
                ArticleSearchBar(
                    searchQuery = state.articleQuery.query,
                    onClear = { onEvent(ArticlesEvent.OnClear) },
                    onSearch = { onEvent(ArticlesEvent.OnSearch) },
                    onSearchQueryChange = { onEvent(ArticlesEvent.OnSearchQueryChange(it)) },
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.paddingLarge)
                        .padding(top = AppTheme.dimens.paddingLarge)
                )
            }

            item(key = "categories") {
                ArticlesCategorySelector(
                    selectedCategory = state.articleQuery.category,
                    onCategorySelected = { onEvent(ArticlesEvent.OnCategorySelected(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(items = state.articles, key = { it.articleUrl }) { article ->
                ArticleCard(
                    article = article,
                    onShare = dropUnlessResumed{ onEvent(ArticlesEvent.OnShare(article)) },
                    onToggleExpand = { onEvent(ArticlesEvent.OnToggleExpand(article)) },
                    onOpenInBrowser = dropUnlessResumed{ onEvent(ArticlesEvent.OnOpenInBrowser(article)) },
                    onNavigateToDetails = dropUnlessResumed{ onEvent(ArticlesEvent.OnNavigateToADetails(article)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimens.paddingLarge)
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
            onEvent = {},
        )
    }
}

