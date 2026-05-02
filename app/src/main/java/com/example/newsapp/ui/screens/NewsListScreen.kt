package com.example.newsapp.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.components.ArticlesLazyColumn
import com.example.newsapp.ui.events.NewsListEvents
import com.example.newsapp.ui.preview.getMockNewsListEvents
import com.example.newsapp.ui.preview.getMockSuccessNewsUiState
import com.example.newsapp.ui.state.NewsUiState
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.viewmodels.NewsViewModel

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(),
    onNavigateToArticleDetails: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val events = remember {
        NewsListEvents(
            onNavigateToArticleDetails = { article ->
                viewModel.setDetailsArticle(article)
                onNavigateToArticleDetails()
            },
            onArticleSelectedCategoryChange = { viewModel.onArticleSelectedCategoryChange(it) },
            onExpandOrCollapseCardClick = { viewModel.onExpandOrCollapseCardClick(it) },
            onShareClick = { viewModel.onShareClick(it) },
            onRefresh = { viewModel.onRefresh() },
            onArticleSearchBarValueChange = { viewModel.onArticleSearchBarValueChange(it) },
            onArticleSearchBarDeleteClick = { viewModel.onArticleSearchBarDeleteClick() },
            onArticleSearchBarSearchClick = { viewModel.onArticleSearchBarSearchClick() },
        )
    }

    when (state) {
        is NewsUiState.Loading -> {
            //todo show loading
        }

        is NewsUiState.Error -> {
            //todo show error
        }

        is NewsUiState.Success -> {
            NewsListContent(
                state = state as NewsUiState.Success,
                events = events,
                modifier = modifier
            )
        }
    }
}

@Composable
fun NewsListContent(
    state: NewsUiState.Success,
    events: NewsListEvents,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = false,
        onRefresh = events.onRefresh,
        modifier = modifier
    ) {
        ArticlesLazyColumn(
            state = state,
            events = events
        )
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
            state = getMockSuccessNewsUiState(),
            events = getMockNewsListEvents()
        )
    }
}

