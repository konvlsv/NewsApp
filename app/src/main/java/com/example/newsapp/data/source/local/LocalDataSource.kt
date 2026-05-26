package com.example.newsapp.data.source.local

import com.example.newsapp.data.source.local.models.ArticleEntity

interface LocalDataSource {

    suspend fun saveDetailsArticle(article: ArticleEntity)
    suspend fun getDetailsArticle(): ArticleEntity
}