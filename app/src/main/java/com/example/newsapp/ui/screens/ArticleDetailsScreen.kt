package com.example.newsapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.theme.NewsAppTheme
import java.lang.reflect.Modifier

@Composable
fun ArticleDetailsScreen(
    modifier: Modifier = Modifier()
) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArticleDetailsScreenPreview() {
    NewsAppTheme() {
        ArticleDetailsScreen()
    }
}