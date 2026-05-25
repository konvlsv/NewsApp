package com.example.newsapp.ui.common.models

data class ArticleUi(
    val id: String,
    val sourceName: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val articleUrl: String,
    val imageUrl: String,
)