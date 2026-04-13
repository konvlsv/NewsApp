package com.example.newsapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.components.ArticleCardCollapsed
import com.example.newsapp.ui.components.ArticleCardExpanded
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlin.random.Random

@Composable
fun NewsListScreen(
    articleList: List<ArticleUi>,
    onCardClick: (ArticleUi) -> Unit,
    onShareClick: (ArticleUi) -> Unit,
    onOpenInBrowserClick: (ArticleUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = articleList.size,
            key = { articleList[it].id },
        ) {
            if (Random.nextBoolean()) {
                ArticleCardExpanded(
                    articleUi = articleList[it],
                    onCardClick = onCardClick,
                    onShareClick = onShareClick,
                    onOpenInBrowserClick = onOpenInBrowserClick
                )
            }else{
                ArticleCardCollapsed(
                    articleUi = articleList[it],
                    onCardClick = onCardClick,
                    onShareClick = onShareClick,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsListScreenPreview() {
    NewsAppTheme() {
        NewsListScreen(
            articleList = getMockArticleUiList(),
            onCardClick = {},
            onShareClick = {},
            onOpenInBrowserClick = {}
        )
    }
}

