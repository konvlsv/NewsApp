package com.example.newsapp.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.components.ArticlesLazyColumn
import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.viewmodels.NewsViewModel

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(),
    onNavigateToArticleDetails: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    NewsListContent(
        onNavigateToArticleDetails = {
            viewModel.setDetailsArticle(it)
            onNavigateToArticleDetails()
            Log.d("MY_LOG", "onNavigateToArticleDetails: $it")
        },
        articleList = state.articles,
        articleSelectedCategory = state.selectedCategory,
        isCardExpanded = { article -> state.expandedCards.contains(article) },
        isRefreshing = state.isLoading,
        articleSearchBarSearchQuery = state.searchQuery,
        onArticleSelectedCategoryChange = { viewModel.onArticleSelectedCategoryChange(it) },
        onExpandOrCollapseCardClick = { viewModel.onExpandOrCollapseCardClick(it) },
        onShareClick = { viewModel.onShareClick(it) },
        onRefresh = { viewModel.onRefresh() },
        onArticleSearchBarValueChange = { viewModel.onArticleSearchBarValueChange(it) },
        onArticleSearchBarDeleteClick = { viewModel.onArticleSearchBarDeleteClick() },
        onArticleSearchBarSearchClick = { viewModel.onArticleSearchBarSearchClick() },
        modifier = modifier
    )
}

@Composable
fun NewsListContent(
    onNavigateToArticleDetails: (ArticleDisplayModel) -> Unit,
    articleList: List<ArticleDisplayModel>,
    onShareClick: (ArticleDisplayModel) -> Unit,
    articleSelectedCategory: ArticleCategory,
    isCardExpanded: (ArticleDisplayModel) -> Boolean,
    onArticleSelectedCategoryChange: (ArticleCategory) -> Unit,
    onExpandOrCollapseCardClick: (ArticleDisplayModel) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    articleSearchBarSearchQuery: String,
    onArticleSearchBarValueChange: (String) -> Unit,
    onArticleSearchBarDeleteClick: () -> Unit,
    onArticleSearchBarSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        ArticlesLazyColumn(
            onNavigateToArticleDetails = onNavigateToArticleDetails,
            articleList = articleList,
            onShareClick = onShareClick,
            isCardExpanded = isCardExpanded,
            articleSelectedCategory = articleSelectedCategory,
            onArticleSelectedCategoryChange = onArticleSelectedCategoryChange,
            onExpandOrCollapseCardClick = onExpandOrCollapseCardClick,
            articleSearchBarSearchQuery = articleSearchBarSearchQuery,
            onArticleSearchBarValueChange = onArticleSearchBarValueChange,
            onArticleSearchBarDeleteClick = onArticleSearchBarDeleteClick,
            onArticleSearchBarSearchClick = onArticleSearchBarSearchClick
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
            articleList = getMockArticleUiList(),
            onShareClick = {},
            articleSelectedCategory = ArticleCategory.GENERAL,
            isCardExpanded = { false },
            isRefreshing = false,
            onRefresh = {},
            onArticleSelectedCategoryChange = {},
            onExpandOrCollapseCardClick = {},
            articleSearchBarSearchQuery = "",
            onArticleSearchBarValueChange = {},
            onArticleSearchBarDeleteClick = {},
            onArticleSearchBarSearchClick = {},
            onNavigateToArticleDetails = {},
        )
    }
}

