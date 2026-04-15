package com.example.newsapp.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.components.ArticlesLazyColumn
import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.viewmodels.NewsViewModel

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel()
) {
    NewsListContent(
        articleList = viewModel.getArticles(),
        onShareClick = { viewModel.onShareClick(it) },
        onOpenInBrowserClick = { viewModel.onOpenInBrowserClick(it) },
        articleSelectedCategory = viewModel.articleSelectedCategory(),
        isCardExpanded = { viewModel.isCardExpanded(it) },
        isRefreshing = viewModel.isRefreshing(),
        onRefresh = { viewModel.onRefresh() },
        onArticleSelectedCategoryChange = { viewModel.onArticleSelectedCategoryChange(it) },
        onExpandOrCollapseCardClick = { viewModel.onExpandOrCollapseCardClick(it) },
        modifier = modifier
    )
}

@Composable
fun NewsListContent(
    articleList: List<ArticleUi>,
    onShareClick: (ArticleUi) -> Unit,
    onOpenInBrowserClick: (ArticleUi) -> Unit,
    articleSelectedCategory: ArticleCategory,
    isCardExpanded: (ArticleUi) -> Boolean,
    onArticleSelectedCategoryChange: (ArticleCategory) -> Unit,
    onExpandOrCollapseCardClick: (ArticleUi) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        ArticlesLazyColumn(
            articleList = articleList,
            onShareClick = onShareClick,
            onOpenInBrowserClick = onOpenInBrowserClick,
            isCardExpanded = isCardExpanded,
            articleSelectedCategory = articleSelectedCategory,
            onArticleSelectedCategoryChange = onArticleSelectedCategoryChange,
            onExpandOrCollapseCardClick = onExpandOrCollapseCardClick
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
            onOpenInBrowserClick = {},
            articleSelectedCategory = ArticleCategory.GENERAL,
            isCardExpanded = { false },
            isRefreshing = false,
            onRefresh = {},
            onArticleSelectedCategoryChange = {},
            onExpandOrCollapseCardClick = {}
        )
    }
}

