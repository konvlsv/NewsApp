package com.example.newsapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.R
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.AppTheme
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCardExpandedContent(
    onNavigateToArticleDetails: (ArticleDisplayModel) -> Unit,
    articleDisplayModel: ArticleDisplayModel,
    onShareClick: (ArticleDisplayModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = AppTheme.dimens.paddingLarge)
            .padding(bottom = AppTheme.dimens.paddingLarge)
            .height(IntrinsicSize.Min),
    ) {
        Text(
            text = articleDisplayModel.content,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onNavigateToArticleDetails(articleDisplayModel) },
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(AppTheme.dimens.buttonElevation),
            ) {
                Text(
                    text = stringResource(R.string.open_details),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            IconButton(onClick = { onShareClick(articleDisplayModel) }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(R.string.share),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
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
fun ArticleCardExpandedContentPreview() {
    NewsAppTheme() {
        ArticleCardExpandedContent(
            articleDisplayModel = getMockArticleUiList().random(),
            onShareClick = {},
            onNavigateToArticleDetails = {}
        )
    }
}