package com.example.newsapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsListScreen(
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
        NewsListScreen()
    }
}

