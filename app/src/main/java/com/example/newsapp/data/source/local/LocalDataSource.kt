package com.example.newsapp.data.source.local

import com.example.newsapp.data.source.local.models.ArticleEntity

interface LocalDataSource {

    suspend fun saveDetailArticle(article: ArticleEntity)
    suspend fun getDetailArticle(): ArticleEntity
}