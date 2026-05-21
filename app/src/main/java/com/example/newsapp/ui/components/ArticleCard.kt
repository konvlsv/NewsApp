package com.example.newsapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.R
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.preview.getMockArticleUiList
import com.example.newsapp.ui.theme.AppTheme
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCard(
    article: ArticleDisplayModel,
    onNavigateToArticleDetails: (ArticleDisplayModel) -> Unit,
    isCardExpanded: (ArticleDisplayModel) -> Boolean,
    onShareClick: (ArticleDisplayModel) -> Unit,
    onExpandOrCollapseCardClick: (ArticleDisplayModel) -> Unit,
    openInBrowserClick: (ArticleDisplayModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppTheme.dimens.cardElevation
        ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .fillMaxWidth()
            .padding(
                horizontal = AppTheme.dimens.paddingLarge,
                vertical = AppTheme.dimens.paddingMedium
            )
    ) {
        Column() {
            ArticleCardCollapsedContent(
                name = article.name,
                title = article.title,
                author = article.author,
                publishedAt = article.publishedAt,
                urlToImage = article.urlToImage,
                onExpandOrCollapseCardClick = { onExpandOrCollapseCardClick(article) },
                onExpandOrCollapseCardIcon = if (isCardExpanded(article)) painterResource(R.drawable.ic_arrow_circle_down)
                else painterResource(R.drawable.ic_arrow_circle_up)
            )
            if (isCardExpanded(article)) {
                ArticleCardExpandedContent(
                    description = article.description,
                    onNavigateToArticleDetails = { onNavigateToArticleDetails(article) },
                    onShareClick = { onShareClick(article) },
                    openInBrowserClick = { openInBrowserClick(article) }
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
            onExpandOrCollapseCardClick = {},
            isCardExpanded = { false },
            onNavigateToArticleDetails = {},
            openInBrowserClick = {}
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
            onExpandOrCollapseCardClick = {},
            isCardExpanded = { true },
            onNavigateToArticleDetails = {},
            openInBrowserClick = {}
        )
    }
}