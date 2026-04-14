package com.example.newsapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.AppTheme
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticlesLazyColumn(
    articleList: List<ArticleUi>,
    onShareClick: (ArticleUi) -> Unit,
    onOpenInBrowserClick: (ArticleUi) -> Unit,
    onArticleSelectedCategoryChange: (ArticleCategory) -> Unit,
    onExpandOrCollapseCardClick: (ArticleUi) -> Unit,
    articleSelectedCategory: ArticleCategory,
    isCardExpanded: (ArticleUi) -> Boolean,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.lazyColumnItemsSpacing)
    ) {
        item {
            ArticlesCategoryLazyRow(
                articleSelectedCategory = articleSelectedCategory,
                onArticleSelectedCategoryChange = onArticleSelectedCategoryChange
            )
        }
        items(
            items = articleList,
            key = { it.id }
        ) { article ->
            ArticleCard(
                article = article,
                onShareClick = onShareClick,
                onOpenInBrowserClick = onOpenInBrowserClick,
                onExpandOrCollapseCardClick = onExpandOrCollapseCardClick,
                isCardExpanded = isCardExpanded,
                modifier = Modifier.padding(horizontal = AppTheme.dimens.paddingLarge)
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
            articleList = getMockArticleUiList(),
            onShareClick = {},
            onOpenInBrowserClick = {},
            onArticleSelectedCategoryChange = {},
            onExpandOrCollapseCardClick = {},
            articleSelectedCategory = ArticleCategory.GENERAL,
            isCardExpanded = { false }
        )
    }
}