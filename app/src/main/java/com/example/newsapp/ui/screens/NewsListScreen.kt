package com.example.newsapp.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.components.ArticleCardCollapsed
import com.example.newsapp.ui.components.ArticleCardExpanded
import com.example.newsapp.ui.components.ArticleSearchBar
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.AppTheme
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsListScreen(
    articleList: List<ArticleUi>,
    onShareClick: (ArticleUi) -> Unit,
    onOpenInBrowserClick: (ArticleUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    // 1. Создаем состояние для хранения ID развернутых карточек
    // Используем Set, чтобы можно было развернуть несколько карточек одновременно
    var expandedCardIds by remember { mutableStateOf(setOf<String>()) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(AppTheme.dimens.paddingExtraLarge),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.lazyColumnItemsSpacing)
    ) {
        item(){
            ArticleSearchBar(
                searchText = "", //todo прокинуть во viewmodel
                onValueChange = {},
                onDeleteClick = {}
            )
        }
        // Используем items(articleList) вместо items(count), это удобнее
        items(
            items = articleList,
            key = { it.id } // Ключ важен для правильной анимации списка
        ) { article ->

            // 2. Проверяем, развернута ли текущая карточка
            val isExpanded = expandedCardIds.contains(article.id)

            // 3. Добавляем анимацию смены размера
            Box(
                modifier = Modifier.animateContentSize()
            ) {
                if (isExpanded) {
                    ArticleCardExpanded(
                        articleUi = article,
                        // При клике на развернутую — удаляем ID из списка (сворачиваем)
                        onCardClick = {
                            expandedCardIds = expandedCardIds - article.id
                        },
                        onShareClick = onShareClick,
                        onOpenInBrowserClick = onOpenInBrowserClick
                    )
                } else {
                    ArticleCardCollapsed(
                        articleUi = article,
                        // При клике на свернутую — добавляем ID в список (разворачиваем)
                        onCardClick = {
                            expandedCardIds = expandedCardIds + article.id
                        },
                        onShareClick = onShareClick,
                    )
                }
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
fun NewsListScreenPreview() {
    NewsAppTheme() {
        NewsListScreen(
            articleList = getMockArticleUiList(),
            onShareClick = {},
            onOpenInBrowserClick = {}
        )
    }
}

