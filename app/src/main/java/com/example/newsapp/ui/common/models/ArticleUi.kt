package com.example.newsapp.ui.common.models


data class ArticleUi(
    val id: Int,
    val sourceName: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val articleUrl: String,
    val imageUrl: String,
) {
    companion object {
        val EMPTY = ArticleUi(
            id = 0,
            sourceName = "",
            author = "",
            content = "",
            description = "",
            publishedAt = "",
            title = "",
            articleUrl = "",
            imageUrl = "",
        )
    }
}