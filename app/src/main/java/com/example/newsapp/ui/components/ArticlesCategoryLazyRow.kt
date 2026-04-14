package com.example.newsapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticlesCategoryLazyRow(
    articleSelectedCategory: ArticleCategory,
    onArticleSelectedCategoryChange: (ArticleCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
    ) {
        items(ArticleCategory.entries) { category ->
            FilterChip(
                selected = articleSelectedCategory == category,
                onClick = { onArticleSelectedCategoryChange(category) },
                label = {
                    Text(
                        text = stringResource(id = category.label),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                shape = MaterialTheme.shapes.medium,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
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
fun ArticlesCategoryLazyRowPreview() {
    NewsAppTheme() {
        ArticlesCategoryLazyRow(
            articleSelectedCategory = ArticleCategory.GENERAL,
            onArticleSelectedCategoryChange = {},
        )
    }
}