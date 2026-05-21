package com.example.newsapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R

@Composable
fun ArticleImage(
    urlToImage: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(urlToImage)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.article_picture),
        placeholder = ColorPainter(Color.LightGray),
        error = ColorPainter(Color.LightGray),
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}