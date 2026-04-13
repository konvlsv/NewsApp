package com.example.newsapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.AppTheme
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCardExpanded(
    articleUi: ArticleUi,
    onCardClick: (ArticleUi) -> Unit,
    onShareClick: (ArticleUi) -> Unit,
    onOpenInBrowserClick: (ArticleUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppTheme.dimens.cardElevation
        ),
        onClick = { onCardClick(articleUi) },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(AppTheme.dimens.paddingLarge)
        ) {
            Box {
                // 1. Сначала картинка (нижний слой)
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Article picture",
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color.Gray) // todo удалить
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                // 2. Затем Row с контентом (верхний слой)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    // Расталкивает элементы в разные стороны
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth() // Обязательно, чтобы занять всю ширину картинки
                        .padding(AppTheme.dimens.paddingLarge)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = articleUi.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            // weight с fill = false позволяет фону облегать только текст
                            .weight(
                                weight = 1f,
                                fill = false
                            )
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f), // Можно добавить прозрачности
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(
                                horizontal = AppTheme.dimens.paddingLarge,
                                vertical = AppTheme.dimens.paddingSmall
                            )
                    )

                    IconButton(
                        onClick = { onShareClick(articleUi) },
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Share",
                        )
                    }
                }
            }
            Text(
                text = articleUi.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(top = AppTheme.dimens.paddingLarge)
                    .fillMaxWidth(),
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge)
            ) {
                Text(
                    text = articleUi.author,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = articleUi.publishedAt,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Text(
                text = articleUi.content,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.paddingLarge),
            )
            Button(
                onClick = { onOpenInBrowserClick(articleUi) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(top = AppTheme.dimens.paddingLarge)
            ) {
                Text(
                    text = "Open in browser",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
)
@Composable
fun ArticleCardCollapsedExpanded() {
    NewsAppTheme() {
        ArticleCardExpanded(
            articleUi = getMockArticleUiList().random(),
            onCardClick = {},
            onShareClick = {},
            onOpenInBrowserClick = {},
        )
    }
}