package com.example.newsapp.data

import com.example.newsapp.ui.models.ArticleDisplayModel

interface DataRepository {

    suspend fun getArticles(
        query: String,
        category: String,
        country: String
    ): List<ArticleDisplayModel>
}