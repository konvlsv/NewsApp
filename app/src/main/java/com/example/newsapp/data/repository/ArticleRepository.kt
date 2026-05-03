package com.example.newsapp.data.repository

import com.example.newsapp.ui.models.ArticleDisplayModel

interface ArticleRepository {

    suspend fun getArticles(
        query: String,
        category: String,
        country: String
    ): List<ArticleDisplayModel>
}