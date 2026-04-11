package com.example.newsapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCardCollapsed(
    modifier: Modifier = Modifier,
){

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArticleCardCollapsedPreview(){
    NewsAppTheme(){
        ArticleCardCollapsed()
    }
}