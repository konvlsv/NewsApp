package com.example.newsapp.data.source.local

import com.example.newsapp.data.source.local.models.ArticleLocalModel

interface LocalDataSource {

    suspend fun saveDetailArticle(article: ArticleLocalModel)
    suspend fun getDetailArticle(): ArticleLocalModel
}