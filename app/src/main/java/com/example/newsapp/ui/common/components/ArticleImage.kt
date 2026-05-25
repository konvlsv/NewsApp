package com.example.newsapp.ui.common.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.newsapp.R

@Composable
fun ArticleImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .size(coil.size.Size.ORIGINAL)
            .lifecycle(androidx.lifecycle.compose.LocalLifecycleOwner.current)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = stringResource(R.string.article_picture),
        placeholder = ColorPainter(Color.LightGray),
        error = ColorPainter(Color.LightGray),
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}