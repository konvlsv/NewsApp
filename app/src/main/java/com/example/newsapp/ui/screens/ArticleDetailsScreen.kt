package com.example.newsapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.newsapp.ui.theme.AppTheme
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.viewmodels.NewsViewModel

@Composable
fun ArticleDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel()
) {

    val article by viewModel.detailsArticle.collectAsStateWithLifecycle()
    if (article != null) {
        Log.d("MY_LOG", "ArticleDetailsScreen: $article")
        Column(
            modifier = modifier
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
                text = article!!.name,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = article!!.description,
                style = MaterialTheme.typography.titleLarge,
            )
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = article!!.author,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = article!!.publishedAt,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Text(
                text = article!!.content,
                style = MaterialTheme.typography.bodyLarge,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { },
                    shape = MaterialTheme.shapes.medium,
                    elevation = ButtonDefaults.buttonElevation(AppTheme.dimens.buttonElevation),
                ) {
                    Text(
                        text = stringResource(R.string.open_in_browser),
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = stringResource(R.string.share),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArticleDetailsScreenPreview() {
    NewsAppTheme() {
        ArticleDetailsScreen()
    }
}