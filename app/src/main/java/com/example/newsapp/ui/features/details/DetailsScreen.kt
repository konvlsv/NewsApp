package com.example.newsapp.ui.features.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.R
import com.example.newsapp.ui.common.models.ArticleUi
import com.example.newsapp.ui.common.preview.getMockArticleUiList
import com.example.newsapp.ui.state.UiState
import com.example.newsapp.ui.common.theme.AppTheme
import com.example.newsapp.ui.common.theme.NewsAppTheme
import com.example.newsapp.ui.common.components.ErrorScreen
import com.example.newsapp.ui.common.components.LoadingScreen

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val currentState = uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Error -> {
            ErrorScreen(message = currentState.data.message)
        }

        is UiState.Success -> {
            DetailsContent(
                article = currentState.data.detail,
                onOpenInBrowserClick = {
                    viewModel.openInBrowser(it.articleUrl)
                },
                onShareClick = {
                    viewModel.shareArticle(it.sourceName, it.description, it.articleUrl)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun DetailsContent(
    article: ArticleUi,
    onOpenInBrowserClick: (ArticleUi) -> Unit,
    onShareClick: (ArticleUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(AppTheme.dimens.paddingLarge)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = stringResource(R.string.article_picture),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
        )
        Text(
            text = article.sourceName,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge)
        )
        Text(
            text = article.description,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge)
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge)
        ) {
            Text(
                text = article.author,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = article.publishedAt,
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Text(
            text = article.content,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(onClick = { onOpenInBrowserClick(article) }) {
                Icon(
                    imageVector = Icons.Default.OpenInBrowser,
                    contentDescription = stringResource(R.string.open_in_browser),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            IconButton(onClick = { onShareClick(article) }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(R.string.share),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailsContentPreview() {
    NewsAppTheme() {
        DetailsContent(
            article = getMockArticleUiList().random(),
            onOpenInBrowserClick = {},
            onShareClick = {}
        )
    }
}