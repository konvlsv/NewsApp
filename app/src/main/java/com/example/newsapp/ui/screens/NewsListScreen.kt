package com.example.newsapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsListScreen(
    articleList: List<ArticleUi>,
    onCardClick: (ArticleUi) -> Unit,
    onShareClick: (ArticleUi) -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
    ) {
        LazyColumn() {

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsListScreenPreview(){
    NewsAppTheme() {
        NewsListScreen(
            articleList = listOf(),
            onCardClick = {},
            onShareClick = {},
        )
    }
}

