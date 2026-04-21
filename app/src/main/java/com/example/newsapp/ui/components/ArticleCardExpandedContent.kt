package com.example.newsapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.R
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.AppTheme
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCardExpandedContent(
    articleUi: ArticleUi,
    onOpenInBrowserClick: (ArticleUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = AppTheme.dimens.paddingLarge)
            .padding(bottom = AppTheme.dimens.paddingLarge)
            .height(IntrinsicSize.Min),
    ) {
        Text(
            text = articleUi.content,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = { onOpenInBrowserClick(articleUi) },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = AppTheme.dimens.paddingLarge)
        ) {
            Text(
                text = stringResource(R.string.open_in_browser),
                style = MaterialTheme.typography.labelMedium,
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
fun ArticleCardExpandedContentPreview() {
    NewsAppTheme() {
        ArticleCardExpandedContent(
            articleUi = getMockArticleUiList().random(),
            onOpenInBrowserClick = {},
        )
    }
}