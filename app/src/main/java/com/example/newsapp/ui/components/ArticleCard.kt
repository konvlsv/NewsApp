package com.example.newsapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCard(
    article: ArticleUi,
    isCardExpanded: Boolean,
    onShareClick: (ArticleUi) -> Unit,
    onExpandOrCollapseCardClick: (ArticleUi) -> Unit,
    onOpenInBrowserClick: (ArticleUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.animateContentSize()) {
        if (isCardExpanded) {
            ArticleCardExpanded(
                articleUi = article,
                onExpandOrCollapseCardClick = onExpandOrCollapseCardClick,
                onShareClick = onShareClick,
                onOpenInBrowserClick = onOpenInBrowserClick
            )
        } else {
            ArticleCardCollapsed(
                articleUi = article,
                onExpandOrCollapseCardClick = onExpandOrCollapseCardClick,
                onShareClick = onShareClick,
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
fun ArticleCardPreviewCollapsed() {
    NewsAppTheme() {
        ArticleCard(
            article = getMockArticleUiList().random(),
            onShareClick = {},
            onOpenInBrowserClick = {},
            onExpandOrCollapseCardClick = {},
            isCardExpanded = false,

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
fun ArticleCardPreviewExpanded() {
    NewsAppTheme() {
        ArticleCard(
            article = getMockArticleUiList().random(),
            onShareClick = {},
            onOpenInBrowserClick = {},
            onExpandOrCollapseCardClick = {},
            isCardExpanded = true
        )
    }
}