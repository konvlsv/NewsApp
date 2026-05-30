package com.example.newsapp.data.source.local

import com.example.newsapp.data.source.local.models.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun saveDetailsArticle(article: ArticleEntity)
    fun getDetailsArticle(): Flow<ArticleEntity>
}