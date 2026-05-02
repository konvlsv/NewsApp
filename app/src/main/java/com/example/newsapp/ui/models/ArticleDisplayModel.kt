package com.example.newsapp.ui.models

data class ArticleDisplayModel(
    val id: String,
    val name: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
)
