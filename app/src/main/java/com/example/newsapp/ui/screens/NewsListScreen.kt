package com.example.newsapp.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.components.ArticlesLazyColumn
import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    //todo ViewModel
) {
    NewsListContent(
        articleList = getMockArticleUiList(), //todo прокинуть во ViewModel
        onShareClick = {}, //todo прокинуть во ViewModel
        onOpenInBrowserClick = {}, //todo прокинуть во ViewModel
        articleSelectedCategory = ArticleCategory.GENERAL,
        isCardExpanded = { false },
        isRefreshing = false,
        onRefresh = {},
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
            onArticleSelectedCategoryChange = {},
            onExpandOrCollapseCardClick = {}
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
            onRefresh = {}
        )
    }
}

