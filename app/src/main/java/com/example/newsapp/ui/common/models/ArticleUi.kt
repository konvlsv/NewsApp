package com.example.newsapp.ui.common.models

data class ArticleUi(
    val id: String,
    val name: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val isExpanded: Boolean = false,
)