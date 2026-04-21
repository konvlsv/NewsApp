package com.example.newsapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.AppTheme
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCard(
    article: ArticleUi,
    isCardExpanded: (ArticleUi) -> Boolean,
    onShareClick: (ArticleUi) -> Unit,
    onExpandOrCollapseCardClick: (ArticleUi) -> Unit,
    onOpenInBrowserClick: (ArticleUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppTheme.dimens.cardElevation
        ),
        onClick = { onExpandOrCollapseCardClick(article) },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier
            .animateContentSize()
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        Crossfade(
            targetState = isCardExpanded,
            label = "CardFade"
        ) { expanded: (ArticleUi) -> Boolean ->
            if (expanded(article)) {
                ArticleCardExpandedContent(
                    articleUi = article,
                    onShareClick = onShareClick,
                    onOpenInBrowserClick = onOpenInBrowserClick
                )
            } else {
                ArticleCardCollapsedContent(
                    articleUi = article,
                    onShareClick = onShareClick,
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
fun ArticleCardPreviewCollapsed() {
    NewsAppTheme() {
        ArticleCard(
            article = getMockArticleUiList().random(),
            onShareClick = {},
            onOpenInBrowserClick = {},
            onExpandOrCollapseCardClick = {},
            isCardExpanded = { false },

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
            isCardExpanded = { true }
        )
    }
}